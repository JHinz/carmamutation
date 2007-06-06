package com.mutation.driver.maven;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import org.apache.maven.plugin.MojoExecutionException;

import junit.framework.TestCase;

import com.retroduction.carma.core.runner.events.listener.SummaryCreatorEventListener.Summary;

public class MavenTestExecuterTestCase extends TestCase {

	public void testExecuteTests() throws MojoExecutionException{
		MavenTestExecuter executer = new MavenTestExecuter();
		
		File reportFile = new File("log/Report.xml");
		File projectbaseDir = new File("../SampleProjectunderTest");
		executer.setClassesDir(new File(projectbaseDir, "target/classes") );
		executer.setTestClassesDir( new File(projectbaseDir, "target/test-classes") );
		executer.setLogFile( new File("log/logfile.log"));
		executer.setReportFile(reportFile);
		executer.setTestNamePattern("Test");
		executer.setDependencyClassPathUrls(new ArrayList<URL>());
		
		if(reportFile.exists()){
			reportFile.delete();
		}
		Summary sum = executer.exeuteTests();
		assertEquals(1, sum.numTests);
		assertEquals(3.0, sum.mutantsPerClass);
		assertEquals(33, Math.round(sum.coverageRatioPercentage));
		assertEquals(1.0, sum.testsPerClass);
		assertEquals(1, sum.numClassesUnderTest);
		assertEquals(3, sum.numMutants);
		assertEquals(2, sum.numSurvivors);
		
		assertTrue("report file does not exist: " +reportFile.getAbsolutePath(), reportFile.exists());
		
	}
}
