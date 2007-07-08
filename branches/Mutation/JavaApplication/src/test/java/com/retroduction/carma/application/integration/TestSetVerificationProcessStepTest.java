package com.retroduction.carma.application.integration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

import org.apache.commons.cli.ParseException;

import com.retroduction.carma.application.Carma;
import com.retroduction.carma.xmlreport.om.MutationRun;
import com.retroduction.carma.xmlreport.utilities.ReportModelLoader;

public class TestSetVerificationProcessStepTest extends TestCase {

	@Override
	public void setUp() {
		File report = new File("target/report.xml");

		if (report.exists()) {
			report.delete();
		}
	}

	public void test_TestSet_Broken_TestNotGreen() throws MalformedURLException, FileNotFoundException, ParseException,
			JAXBException, InterruptedException {

		ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();

		Carma.main(new String[] { "-uc", "src/test/it/it0005/carma.properties" });

		File report = new File("target/report.xml");

		assertTrue("Report.xml has not been created.", report.exists());

		MutationRun run = new ReportModelLoader().loadReportModel(report);

		assertNotNull(run.getProcessingInfo().getStart());
		assertNotNull(run.getProcessingInfo().getDuration());
		assertNotNull(run.getProcessingInfo().getEnd());

		assertEquals("Wrong mutation count", 1, run.getMutationRatio().getMutationCount());
		assertEquals("Wrong survivor count", 1, run.getMutationRatio().getSurvivorCount());

		List<String> brokenTests = run.getBrokenTests();

		assertEquals("Broken test has not been reported", 1, brokenTests.size());
		assertEquals("Broken test has not been reported", "AnotherSampleClassUsingAnnotation", brokenTests.get(0));

		assertSame("Mutation ClassLoader probably still in use (legacy artifact of previous testcase?)",
				originalClassLoader, Thread.currentThread().getContextClassLoader());

	}

	public void test_TestSetSane_SingleFolder() throws MalformedURLException, FileNotFoundException, ParseException, JAXBException,
			InterruptedException {

		ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();

		Carma.main(new String[] { "-uc", "src/test/it/it0006/carma.properties" });

		File report = new File("target/report.xml");

		assertTrue("Report.xml has not been created.", report.exists());

		MutationRun run = new ReportModelLoader().loadReportModel(report);

		assertNotNull(run.getProcessingInfo().getStart());
		assertNotNull(run.getProcessingInfo().getDuration());
		assertNotNull(run.getProcessingInfo().getEnd());

		assertEquals("Wrong mutation count", 1, run.getMutationRatio().getMutationCount());
		assertEquals("Wrong survivor count", 1, run.getMutationRatio().getSurvivorCount());

		assertSame("Mutation ClassLoader probably still in use (legacy artifact of previous testcase?)",
				originalClassLoader, Thread.currentThread().getContextClassLoader());

	}

	public void test_TestSetSane_MultipleFolders() throws MalformedURLException, FileNotFoundException, ParseException,
			JAXBException, InterruptedException {

		ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();

		Carma.main(new String[] { "-uc", "src/test/it/it0007/carma.properties" });

		File report = new File("target/report.xml");

		assertTrue("Report.xml has not been created.", report.exists());

		MutationRun run = new ReportModelLoader().loadReportModel(report);

		assertNotNull(run.getProcessingInfo().getStart());
		assertNotNull(run.getProcessingInfo().getDuration());
		assertNotNull(run.getProcessingInfo().getEnd());

		assertEquals("Wrong mutation count", 2, run.getMutationRatio().getMutationCount());
		assertEquals("Wrong survivor count", 2, run.getMutationRatio().getSurvivorCount());

		List<String> brokenTests = run.getBrokenTests();

		assertEquals("Broken test has not been reported", 0, brokenTests.size());

		assertSame("Mutation ClassLoader probably still in use (legacy artifact of previous testcase?)",
				originalClassLoader, Thread.currentThread().getContextClassLoader());

	}

}
