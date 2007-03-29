package com.mutation.events.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.mutation.events.IEvent;
import com.mutation.events.IEventListener;
import com.mutation.events.ProcessingClassUnderTest;
import com.mutation.events.ProcessingMutationOperator;
import com.mutation.report.om.MutationOperator;
import com.mutation.report.om.MutationRun;
import com.mutation.report.om.MutationSet;

public class ReportEventListener implements IEventListener {

	private MutationRun report;

	private MutationSet currentSet;

	private MutationOperator currentOperator;

	private String outputFile;

	public ReportEventListener(String fileName) throws JAXBException {

		System.out.print("Initializing XML report");

		report = new MutationRun();
		this.outputFile = fileName;

	}

	public void destroy() {

		System.out.print("Finishing XML report");
		try {
			JAXBContext context = JAXBContext.newInstance(MutationRun.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(report, new FileOutputStream(new File(outputFile)));
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void notifyEvent(IEvent event) {

		System.out.println("Added event " + event.toString() + " to XML report");

		if (event instanceof ProcessingClassUnderTest) {

			ProcessingClassUnderTest eventObj = (ProcessingClassUnderTest) event;

			currentSet = new MutationSet();
			currentSet.setTargetClass(eventObj.getClassUnderTest());

			report.getMutationSet().add(currentSet);
			return;

		}

		if (event instanceof ProcessingMutationOperator) {

			ProcessingMutationOperator eventObj = (ProcessingMutationOperator) event;

			currentOperator = new MutationOperator();
			currentOperator.setName(eventObj.getOperatorName());

			currentSet.getMutation().add(currentOperator);

			return;

		}

	}

}
