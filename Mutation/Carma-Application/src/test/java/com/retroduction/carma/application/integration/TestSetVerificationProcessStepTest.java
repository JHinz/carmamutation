package com.retroduction.carma.application.integration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

import org.apache.commons.cli.ParseException;

import com.mutation.report.loader.ReportModelLoader;
import com.mutation.report.om.MutationRun;
import com.retroduction.carma.application.BasicDriver;

public class TestSetVerificationProcessStepTest extends TestCase {

	public void setUp() {
		File report = new File("target/report.xml");

		if (report.exists()) {
			report.delete();
		}
	}

	public void test_TestSet_Broken() throws MalformedURLException, FileNotFoundException, ParseException,
			JAXBException, InterruptedException {

		BasicDriver.main(new String[] { "-uc", "src/test/it/it0005/config.xml" });

		File report = new File("target/report.xml");

		assertTrue("Report.xml has not been created.", report.exists());

		MutationRun run = new ReportModelLoader().loadReportModel(report);

		assertNotNull(run.getProcessingInfo().getStart());
		assertNotNull(run.getProcessingInfo().getDuration());
		assertNotNull(run.getProcessingInfo().getEnd());

		assertEquals("Wrong mutation count", 0, run.getMutationRatio().getMutationCount());
		assertEquals("Wrong survivor count", 0, run.getMutationRatio().getSurvivorCount());

	}

	public void test_TestSet_Sane() throws MalformedURLException, FileNotFoundException, ParseException, JAXBException,
			InterruptedException {

		BasicDriver.main(new String[] { "-uc", "src/test/it/it0006/config.xml" });

		File report = new File("target/report.xml");

		assertTrue("Report.xml has not been created.", report.exists());

		MutationRun run = new ReportModelLoader().loadReportModel(report);

		assertNotNull(run.getProcessingInfo().getStart());
		assertNotNull(run.getProcessingInfo().getDuration());
		assertNotNull(run.getProcessingInfo().getEnd());

		assertTrue("Wrong mutation count", run.getMutationRatio().getMutationCount() > 0);
		assertTrue("Wrong survivor count", run.getMutationRatio().getSurvivorCount() > 0);

	}
	

	public void test_TestSet_Broken2() throws MalformedURLException, FileNotFoundException, ParseException,
			JAXBException, InterruptedException {

		BasicDriver.main(new String[] { "-uc", "src/test/it/it0005/config.xml" });

		File report = new File("target/report.xml");

		assertTrue("Report.xml has not been created.", report.exists());

		MutationRun run = new ReportModelLoader().loadReportModel(report);

		assertNotNull(run.getProcessingInfo().getStart());
		assertNotNull(run.getProcessingInfo().getDuration());
		assertNotNull(run.getProcessingInfo().getEnd());

		assertEquals("Wrong mutation count", 0, run.getMutationRatio().getMutationCount());
		assertEquals("Wrong survivor count", 0, run.getMutationRatio().getSurvivorCount());

	}
	
}
