package com.retroduction.carma.mavenplugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import junit.framework.TestCase;

import com.retroduction.carma.application.CarmaTestExecuter;
import com.retroduction.carma.eventlisteners.SummaryCreatorEventListener.Summary;

public class CarmaTestExecuterTestCase extends TestCase {

	public void testExecuteTests() throws MalformedURLException{
		CarmaTestExecuter executer = new CarmaTestExecuter();
		
		File reportFile = new File("target/it/it0001/log/Report.xml");
		File projectbaseDir = new File("./");
		executer.setClassesDir(new File(projectbaseDir, "src/test/it/it0001/target/classes") );
		executer.setTestClassesDir( new File(projectbaseDir, "src/test/it/it0001/target/test-classes") );
		executer.setReportFile(reportFile);
		executer.setConfigFile(new File("src/test/it/it0001/carma.properties"));
		executer.setDependencyClassPathUrls(new ArrayList<URL>());
		
		if(reportFile.exists()){
			reportFile.delete();
		}
		
		Summary sum = executer.executeTests();
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
