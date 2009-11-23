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

package com.retroduction.carma.integration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import junit.framework.TestCase;

import com.retroduction.carma.application.CarmaTestExecuter;
import com.retroduction.carma.eventlisteners.SummaryCreatorEventListener.Summary;

public class CarmaTestExecuterTestCase extends TestCase {

	public void testExecuteTests() throws MalformedURLException {
		CarmaTestExecuter executer = new CarmaTestExecuter();

		File reportFile = new File("target/it/org/retroduction/carma/integration/it0001/log/Report.xml");
		File projectbaseDir = new File("./");
		executer.setClassesDir(new File(projectbaseDir, "src/test/it/org/retroduction/carma/integration/it0001/target/classes"));
		executer.setTestClassesDir(new File(projectbaseDir, "src/test/it/org/retroduction/carma/integration/it0001/target/test-classes"));
		executer.setReportFile(reportFile);
		executer.setConfigFile(new File("src/test/it/org/retroduction/carma/integration/it0001/carma.properties"));
		executer.setDependencyClassPathUrls(new ArrayList<URL>());

		if (reportFile.exists()) {
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

		assertTrue("report file does not exist: " + reportFile.getAbsolutePath(), reportFile.exists());

	}
}
