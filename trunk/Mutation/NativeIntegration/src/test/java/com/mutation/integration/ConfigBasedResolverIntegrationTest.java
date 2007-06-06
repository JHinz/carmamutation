package com.mutation.integration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

import org.apache.commons.cli.ParseException;

import com.mutation.BasicDriver;
import com.mutation.report.loader.ReportModelLoader;
import com.mutation.report.om.MutationRun;

public class ConfigBasedResolverIntegrationTest extends TestCase {

	public void test() throws MalformedURLException, FileNotFoundException, ParseException, JAXBException,
			InterruptedException {

		File report = new File("target/report.xml");

		if (report.exists()) {
			report.delete();
		}

		BasicDriver.main(new String[] { "-uc", "src/test/it/it0004/config.xml" });

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
}
