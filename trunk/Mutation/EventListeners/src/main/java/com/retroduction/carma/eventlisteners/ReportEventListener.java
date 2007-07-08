package com.retroduction.carma.eventlisteners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.api.eventlisteners.IEventListener;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingClassUnderTest;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingClassUnderTestFinished;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingMutant;
import com.retroduction.carma.core.api.eventlisteners.om.TestSetNotSane;
import com.retroduction.carma.core.api.eventlisteners.om.TestsExecuted;
import com.retroduction.carma.eventlisteners.util.StatisticalReportAnalyzer;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.Mutant;
import com.retroduction.carma.xmlreport.om.MutationRun;
import com.retroduction.carma.xmlreport.om.ProcessingInfo;

public class ReportEventListener implements IEventListener {

	private Log log = LogFactory.getLog(ReportEventListener.class);

	MutationRun run;

	private ClassUnderTest currentClassUnderTestSubReport;

	private Mutant currentMutantReport;

	private String outputFile;

	private GregorianCalendar calendar;

	private long classProcessingStart;

	private long runProcessingStart;

	private long runProcessingEnd;

	private boolean writeTimingInfo;

	
	public ReportEventListener(String fileName) throws JAXBException {

		this.log.info("Initializing XML report: " + fileName);

		this.run = new MutationRun();
		this.outputFile = fileName;

		this.calendar = new GregorianCalendar();
		this.runProcessingStart = System.currentTimeMillis();

	}

	public ReportEventListener(String fileName, boolean writeTimingInfo) throws JAXBException {
		this(fileName);
		this.writeTimingInfo = writeTimingInfo;
	}

	public void destroy() {

		this.log.info("Finishing XML report");

		this.runProcessingEnd = System.currentTimeMillis();

		new StatisticalReportAnalyzer().enhanceReport(this.run);

		try {
			if (this.writeTimingInfo) {
				ProcessingInfo info = this.createTimingInformation(this.runProcessingStart, this.runProcessingEnd);
				this.run.setProcessingInfo(info);
			}
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		try {
			JAXBContext context = JAXBContext.newInstance(MutationRun.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(this.run, new FileOutputStream(new File(this.outputFile)));
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void notifyEvent(IEvent event) {

		if (event instanceof ProcessingClassUnderTest) {
			this.classProcessingStart = System.currentTimeMillis();
			ProcessingClassUnderTest eventObj = (ProcessingClassUnderTest) event;
			ClassUnderTest clazz = new ClassUnderTest();
			clazz.setClassName(eventObj.getClassUnderTest().getClassName());
			clazz.setPackageName(eventObj.getClassUnderTest().getPackageName());
			clazz.setBaseClassFile(eventObj.getClassUnderTest().getClassFile());
			this.currentClassUnderTestSubReport = clazz;
			return;
		}

		if (event instanceof ProcessingClassUnderTestFinished) {
			try {
				if (this.writeTimingInfo) {
					ProcessingInfo info = this.createTimingInformation(this.classProcessingStart, System.currentTimeMillis());
					this.currentClassUnderTestSubReport.setProcessingInfo(info);
				}
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			this.run.getClassUnderTest().add(this.currentClassUnderTestSubReport);
			return;
		}

		if (event instanceof ProcessingMutant) {
			com.retroduction.carma.core.api.testrunners.om.Mutant mutant = ((ProcessingMutant) event).getMutant();
			Mutant mutantInfo = new Mutant();
			mutantInfo.setName(mutant.getName());
			mutantInfo.setBaseSourceLine(mutant.getSourceMapping().getLineNo());

			if (mutant.getTransitionGroup() != null)
				mutantInfo.setTransitionGroup(mutant.getTransitionGroup().getName());

			if (mutant.getTransition() != null)
				mutantInfo.setTransition(mutant.getTransition().getName());
			this.currentMutantReport = mutantInfo;
			this.currentClassUnderTestSubReport.getMutant().add(this.currentMutantReport);
			this.currentClassUnderTestSubReport.setBaseSourceFile(((ProcessingMutant) event).getMutant().getSourceMapping()
					.getSourceFile());
			return;
		}

		if (event instanceof TestsExecuted) {

			TestsExecuted eventObj = (TestsExecuted) event;
			this.currentMutantReport.setSurvived(eventObj.getMutant().isSurvived());
			this.currentMutantReport.getKillerTests().addAll(eventObj.getMutant().getKillerTestNames());
			// TODO not very beautiful here - is executed for each mutant for
			// relevant for whole class
			this.currentClassUnderTestSubReport.getExecutedTests().clear();
			this.currentClassUnderTestSubReport.getExecutedTests().addAll(eventObj.getMutant().getExecutedTestsNames());

			return;

		}

		if (event instanceof TestSetNotSane) {
			TestSetNotSane eventObj = (TestSetNotSane) event;
			this.run.getBrokenTests().addAll(eventObj.getTestCaseName());
			return;
		}

	}

	ProcessingInfo createTimingInformation(long classProcessingStart, long classProcessingEnd)
			throws DatatypeConfigurationException {
		ProcessingInfo info = new ProcessingInfo();
		info.setDuration(classProcessingEnd - classProcessingStart);
		this.calendar.setTimeInMillis(classProcessingStart);
		info.setStart(DatatypeFactory.newInstance().newXMLGregorianCalendar(this.calendar));
		this.calendar.setTimeInMillis(classProcessingEnd);
		info.setEnd(DatatypeFactory.newInstance().newXMLGregorianCalendar(this.calendar));
		return info;
	}
	
	public boolean isWriteTimingInfo() {
		return this.writeTimingInfo;
	}

	public void setWriteTimingInfo(boolean writeTimingInfo) {
		this.writeTimingInfo = writeTimingInfo;
	}


}
