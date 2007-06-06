package com.mutation.integration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.apache.commons.cli.ParseException;

import com.mutation.BasicDriver;

public class ConfigBasedResolverIntegrationTest extends TestCase {

	public void test() throws MalformedURLException, FileNotFoundException, ParseException {

		File report = new File("report.xml");

		if (report.exists()) {
			report.delete();
		}

		BasicDriver driver = new BasicDriver();

		BasicDriver.main(new String[] { "-uc", "src/test/it/it0004/config.xml" });

		driver = null;
		System.out.println(report.getAbsoluteFile());

		File newreport = new File("report.xml");

		assertTrue("Report.xml has not been created.", newreport.exists());
	}

}
