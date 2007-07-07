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
	private File refDir = new File(itBaseDir, "reference");

	protected void setUp() throws Exception {
		super.setUp();
		deleteDirectory(outputDirectory);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test() throws FileNotFoundException, JAXBException{
		SingleReportGenerator generator = new SingleReportGenerator();
		File reportFile = new File(itBaseDir, "carmareport.xml");
		MutationRun report = new ReportModelLoader().loadReportModel(reportFile );
		
		List<File> sourceFolders = new ArrayList<File>();
		sourceFolders.add(new File(itBaseDir, "src"));
		generator.perform(report, outputDirectory, sourceFolders );
		
		
		assertFileMatch("css/main.css");
		assertFileMatch("css/source-viewer.css");
		assertFileMatch("index.html");
		assertFileMatch("packages.html");
		assertFileMatch("classlist.html");
		assertFileMatch("summary.html");
		assertFileMatch("summary-sample.html");
		assertFileMatch("summary-sample.other.html");
		assertFileMatch("summary-sample.sub.html");
		assertFileMatch("summary-sample.sub.sub2.html");
	}
	
	private void assertFileMatch(String file){
		assertBinaryEquals(file, new File(refDir, file), new File(outputDirectory, file));
	}
	
	static public boolean deleteDirectory(File path) {
	    if( path.exists() ) {
	      File[] files = path.listFiles();
	      for(int i=0; i<files.length; i++) {
	         if(files[i].isDirectory()) {
	           deleteDirectory(files[i]);
	         }
	         else {
	           files[i].delete();
	         }
	      }
	    }
	    return( path.delete() );
	  }
}
