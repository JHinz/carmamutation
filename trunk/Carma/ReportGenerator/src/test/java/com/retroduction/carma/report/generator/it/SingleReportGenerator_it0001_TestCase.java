/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.it;

import static junitx.framework.FileAssert.assertBinaryEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

import com.retroduction.carma.report.generator.SingleReportGenerator;
import com.retroduction.carma.xmlreport.om.MutationRun;
import com.retroduction.carma.xmlreport.utilities.ReportModelLoader;

public class SingleReportGenerator_it0001_TestCase extends TestCase {
	private File outputDirectory = new File("target/it_results/it0001");
	private File itBaseDir = new File("src/test/it/it0001");
	private File refDir = new File(this.itBaseDir, "reference");

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		deleteDirectory(this.outputDirectory);

	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test() throws FileNotFoundException, JAXBException{
		SingleReportGenerator generator = new SingleReportGenerator();
		File reportFile = new File(this.itBaseDir, "carmareport.xml");
		MutationRun report = new ReportModelLoader().loadReportModel(reportFile );
		
		List<File> sourceFolders = new ArrayList<File>();
		sourceFolders.add(new File(this.itBaseDir, "src"));
		generator.perform(report, this.outputDirectory, sourceFolders );
		
		
		this.assertFileMatch("css/main.css");
		this.assertFileMatch("css/sources.css");
		this.assertFileMatch("index.html");
		this.assertFileMatch("packages.html");
		this.assertFileMatch("classlist.html");
		this.assertFileMatch("summary.html");
		this.assertFileMatch("summary-sample.html");
		this.assertFileMatch("summary-sample.other.html");
		this.assertFileMatch("summary-sample.sub.html");
		this.assertFileMatch("summary-sample.sub.sub2.html");
	}
	
	private void assertFileMatch(String file){
		assertBinaryEquals(file, new File(this.refDir, file), new File(this.outputDirectory, file));
	}
	
	static public boolean deleteDirectory(File path) {
	    if( path.exists() ) {
	      File[] files = path.listFiles();
	      for (File element : files) {
	         if(element.isDirectory()) {
	           deleteDirectory(element);
	         }
	         else {
	           element.delete();
	         }
	      }
	    }
	    return( path.delete() );
	  }
}
