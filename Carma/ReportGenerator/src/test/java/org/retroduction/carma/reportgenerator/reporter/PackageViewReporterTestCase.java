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

package org.retroduction.carma.reportgenerator.reporter;

import java.io.StringWriter;
import java.util.HashMap;

import junit.framework.TestCase;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRatio;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * @author arau
 * 
 */
public class PackageViewReporterTestCase extends TestCase {

	private final String EOF_CHAR = System.getProperty("line.separator");

	public void test() {

		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("ingoreExternalTemplates", new Object());

		PackageViewReporter reporter = new PackageViewReporter(context);

		MutationRun report = new MutationRun();

		{
			ClassUnderTest clazz = new ClassUnderTest();
			clazz.setPackageName("package1");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(5);
			ratio.setSurvivorCount(3);

			clazz.setMutationRatio(ratio);

			report.getClassUnderTest().add(clazz);
		}

		{
			ClassUnderTest clazz = new ClassUnderTest();
			clazz.setPackageName("anotherPackage");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(7);
			ratio.setSurvivorCount(7);

			clazz.setMutationRatio(ratio);

			report.getClassUnderTest().add(clazz);
		}

		{
			ClassUnderTest clazz = new ClassUnderTest();
			clazz.setPackageName("package1");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(3);
			ratio.setSurvivorCount(1);

			clazz.setMutationRatio(ratio);

			report.getClassUnderTest().add(clazz);
		}

		StringWriter outputWriter = new StringWriter();

		reporter.generateReport(report, outputWriter);

		StringBuffer expectedResult = new StringBuffer();

		expectedResult.append("<html>").append(EOF_CHAR);
		expectedResult.append("<body>").append(EOF_CHAR);
		expectedResult.append("</body>").append(EOF_CHAR);
		expectedResult.append("</html>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());
		System.out.println(outputWriter.toString());
	}

	public void test_CoverageRatioUndefined() {

		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("ingoreExternalTemplates", new Object());

		PackageViewReporter reporter = new PackageViewReporter(context);

		MutationRun report = new MutationRun();

		{
			ClassUnderTest clazz = new ClassUnderTest();
			clazz.setPackageName("package1");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(0);
			ratio.setSurvivorCount(0);

			clazz.setMutationRatio(ratio);

			report.getClassUnderTest().add(clazz);
		}

		StringWriter outputWriter = new StringWriter();

		reporter.generateReport(report, outputWriter);

		StringBuffer expectedResult = new StringBuffer();

		expectedResult.append("<html>").append(EOF_CHAR);
		expectedResult.append("<body>").append(EOF_CHAR);

		expectedResult.append("</body>").append(EOF_CHAR);
		expectedResult.append("</html>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());
		System.out.println(outputWriter.toString());
	}
}
