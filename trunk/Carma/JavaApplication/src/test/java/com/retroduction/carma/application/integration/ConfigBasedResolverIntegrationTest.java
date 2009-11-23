/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */

package com.retroduction.carma.application.integration;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

import org.apache.commons.cli.ParseException;

import com.retroduction.carma.application.Carma;
import com.retroduction.carma.xmlreport.om.MutationRun;
import com.retroduction.carma.xmlreport.utilities.ReportModelLoader;

public class ConfigBasedResolverIntegrationTest extends TestCase {

	public void test() throws MalformedURLException, FileNotFoundException, ParseException, JAXBException,
			InterruptedException {

		ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();

		File report = new File("target/report.xml");

		if (report.exists()) {
			report.delete();
		}

		Carma.main(new String[] { "-uc", "src/test/it/it0004/carma.properties" });

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

		Carma.main(new String[] { "-uc", "src/test/it/it0007/carma.properties" });

		assertTrue("Report.xml has not been created.", report.exists());

		report = new File("target/report.xml");

		MutationRun run = new ReportModelLoader().loadReportModel(report);

		assertEquals("Wrong class under test count", 2, run.getClassUnderTest().size());

		assertSame("Mutation ClassLoader probably still in use (legacy artifact of previous testcase?)",
				originalClassLoader, Thread.currentThread().getContextClassLoader());

	}
}
