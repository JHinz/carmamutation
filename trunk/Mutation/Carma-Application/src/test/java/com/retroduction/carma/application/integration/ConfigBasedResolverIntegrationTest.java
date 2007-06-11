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
import com.retroduction.carma.core.testrunner.MutationClassLoader;

public class ConfigBasedResolverIntegrationTest extends TestCase {

	public void test() throws MalformedURLException, FileNotFoundException, ParseException, JAXBException,
			InterruptedException {

		assertFalse("Mutation ClassLoader still in use (legacy artifact of previous runs?)", MutationClassLoader.class
				.getName().equals(Thread.currentThread().getContextClassLoader().getClass().getName()));

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

	}

	public void test_MultipleClassesAndTestClassDirectories() throws MalformedURLException, FileNotFoundException,
			ParseException, JAXBException, InterruptedException {

		assertFalse("Mutation ClassLoader still in use (legacy artifact of previous runs?)", MutationClassLoader.class
				.getName().equals(Thread.currentThread().getContextClassLoader().getClass().getName()));

		File report = new File("target/report.xml");

		if (report.exists()) {
			report.delete();
		}

		Carma.main(new String[] { "-uc", "src/test/it/it0007/config.xml" });

		assertTrue("Report.xml has not been created.", report.exists());

		report = new File("target/report.xml");

		MutationRun run = new ReportModelLoader().loadReportModel(report);

		assertEquals("Wrong class under test count", 2, run.getClassUnderTest().size());
		assertEquals("Wrong class under test name", "SampleClass1", run.getClassUnderTest().get(0).getClassName());
		assertEquals("Wrong class under test name", "SampleClass2", run.getClassUnderTest().get(1).getClassName());
		
		assertEquals("Wrong class under test name", "SampleTestClass1", run.getClassUnderTest().get(0).getExecutedTests().get(0));
		assertEquals("Wrong class under test name", "SampleTestClass2", run.getClassUnderTest().get(1).getExecutedTests().get(0));

	}
}
