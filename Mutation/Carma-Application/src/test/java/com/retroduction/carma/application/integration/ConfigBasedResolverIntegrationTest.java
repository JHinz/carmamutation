package com.retroduction.carma.application.integration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

import org.apache.commons.cli.ParseException;

import com.mutation.report.loader.ReportModelLoader;
import com.mutation.report.om.MutationRun;
import com.retroduction.carma.application.Carma;

public class ConfigBasedResolverIntegrationTest extends TestCase {

	public void test() throws MalformedURLException, FileNotFoundException, ParseException, JAXBException,
			InterruptedException {

		ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();

		File report = new File("target/report.xml");

		if (report.exists()) {
			report.delete();
		}

		Carma.main(new String[] { "-uc", "src/test/it/it0004/config.xml" });

		assertTrue("Report.xml has not been created.", report.exists());

		report = new File("target/report.xml");

		MutationRun run = new ReportModelLoader().loadReportModel(report);

		assertNotNull(run.getProcessingInfo().getStart());
		assertNotNull(run.getProcessingInfo().getDuration());
		assertNotNull(run.getProcessingInfo().getEnd());

		assertEquals("Wrong mutation count", 1, run.getMutationRatio().getMutationCount());
		assertEquals("Wrong survivor count", 1, run.getMutationRatio().getSurvivorCount());

		assertEquals("Wrong class under test count", 1, run.getClassUnderTest().size());
		assertEquals("Wrong class under test name", "SampleClassUsingAnnotation", run.getClassUnderTest().get(0)
				.getClassName());

		assertSame("Mutation ClassLoader probably still in use (legacy artifact of previous testcase?)",
				originalClassLoader, Thread.currentThread().getContextClassLoader());

	}

	public void test_MultipleClassesAndTestClassDirectories() throws MalformedURLException, FileNotFoundException,
			ParseException, JAXBException, InterruptedException {

		ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();

		File report = new File("target/report.xml");

		if (report.exists()) {
			report.delete();
		}

		Carma.main(new String[] { "-uc", "src/test/it/it0007/config.xml" });

		assertTrue("Report.xml has not been created.", report.exists());

		report = new File("target/report.xml");

		MutationRun run = new ReportModelLoader().loadReportModel(report);

		assertEquals("Wrong class under test count", 2, run.getClassUnderTest().size());

		assertSame("Mutation ClassLoader probably still in use (legacy artifact of previous testcase?)",
				originalClassLoader, Thread.currentThread().getContextClassLoader());

	}
}
