/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
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
