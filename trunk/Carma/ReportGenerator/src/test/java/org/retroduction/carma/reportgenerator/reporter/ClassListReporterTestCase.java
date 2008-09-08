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
import java.io.Writer;
import java.util.HashMap;

import junit.framework.TestCase;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRatio;
import com.retroduction.carma.xmlreport.om.MutationRun;

public class ClassListReporterTestCase extends TestCase {

	public void test_NoClasses() {

		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("ingoreExternalTemplates", new Object());

		ClassListReporter ctt = new ClassListReporter(context);

		MutationRun report = new MutationRun();

		Writer outputWriter = new StringWriter();

		ctt.generateReport(report, "package1", outputWriter);

		StringBuffer expectedResult = new StringBuffer();

		expectedResult.append("<html>").append("\n");
		expectedResult.append("<body>").append("\n");
		expectedResult.append("<table>").append("\n");
		expectedResult.append("<thead>").append("\n");
		expectedResult.append("<tr>").append("\n");
		expectedResult.append("<td>Class</td>").append("\n");
		expectedResult.append("<td>Coverage Level</td>").append("\n");
		expectedResult.append("<td>Mutation Count</td>").append("\n");
		expectedResult.append("<td>Survived Mutations Count</td>").append("\n");
		expectedResult.append("<td>Defeated Mutations Count</td>").append("\n");
		expectedResult.append("</tr>").append("\n");
		expectedResult.append("</thead>").append("\n");
		expectedResult.append("<tbody>").append("\n");
		expectedResult.append("</tbody>").append("\n");
		expectedResult.append("</table>").append("\n");
		expectedResult.append("</body>").append("\n");
		expectedResult.append("</html>").append("\n");

		assertEquals(expectedResult.toString(), outputWriter.toString());
	}
	
	

	public void test_1Class() {

		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("ingoreExternalTemplates", new Object());

		ClassListReporter ctt = new ClassListReporter(context);

		MutationRun report = new MutationRun();

		{
			ClassUnderTest classUnderTest = new ClassUnderTest();
			classUnderTest.setClassName("MyClass");
			classUnderTest.setPackageName("package1");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(42);
			ratio.setSurvivorCount(21);

			classUnderTest.setMutationRatio(ratio);

			report.getClassUnderTest().add(classUnderTest);
		}

		Writer outputWriter = new StringWriter();

		ctt.generateReport(report, "package1", outputWriter);

		StringBuffer expectedResult = new StringBuffer();

		expectedResult.append("<html>").append("\n");
		expectedResult.append("<body>").append("\n");
		expectedResult.append("<table>").append("\n");
		expectedResult.append("<thead>").append("\n");
		expectedResult.append("<tr>").append("\n");
		expectedResult.append("<td>Class</td>").append("\n");
		expectedResult.append("<td>Coverage Level</td>").append("\n");
		expectedResult.append("<td>Mutation Count</td>").append("\n");
		expectedResult.append("<td>Survived Mutations Count</td>").append("\n");
		expectedResult.append("<td>Defeated Mutations Count</td>").append("\n");
		expectedResult.append("</tr>").append("\n");
		expectedResult.append("</thead>").append("\n");
		expectedResult.append("<tbody>").append("\n");
		expectedResult.append("<tr>").append("\n");
		expectedResult.append("<td><a href=\"package1.MyClass.html\">MyClass</a></td>").append("\n");
		expectedResult.append("<td>50 %</td>").append("\n");
		expectedResult.append("<td>42</td>").append("\n");
		expectedResult.append("<td>21</td>").append("\n");
		expectedResult.append("<td>21</td>").append("\n");
		expectedResult.append("</tr>").append("\n");
		expectedResult.append("</tbody>").append("\n");
		expectedResult.append("</table>").append("\n");
		expectedResult.append("</body>").append("\n");
		expectedResult.append("</html>").append("\n");

		assertEquals(expectedResult.toString(), outputWriter.toString());
	}
	
	public void test_1Class_NoMutations() {

		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("ingoreExternalTemplates", new Object());

		ClassListReporter ctt = new ClassListReporter(context);

		MutationRun report = new MutationRun();

		{
			ClassUnderTest classUnderTest = new ClassUnderTest();
			classUnderTest.setClassName("MyClass");
			classUnderTest.setPackageName("package1");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(0);
			ratio.setSurvivorCount(0);

			classUnderTest.setMutationRatio(ratio);

			report.getClassUnderTest().add(classUnderTest);
		}

		Writer outputWriter = new StringWriter();

		ctt.generateReport(report, "package1", outputWriter);

		StringBuffer expectedResult = new StringBuffer();

		expectedResult.append("<html>").append("\n");
		expectedResult.append("<body>").append("\n");
		expectedResult.append("<table>").append("\n");
		expectedResult.append("<thead>").append("\n");
		expectedResult.append("<tr>").append("\n");
		expectedResult.append("<td>Class</td>").append("\n");
		expectedResult.append("<td>Coverage Level</td>").append("\n");
		expectedResult.append("<td>Mutation Count</td>").append("\n");
		expectedResult.append("<td>Survived Mutations Count</td>").append("\n");
		expectedResult.append("<td>Defeated Mutations Count</td>").append("\n");
		expectedResult.append("</tr>").append("\n");
		expectedResult.append("</thead>").append("\n");
		expectedResult.append("<tbody>").append("\n");
		expectedResult.append("<tr>").append("\n");
		expectedResult.append("<td><a href=\"package1.MyClass.html\">MyClass</a></td>").append("\n");
		expectedResult.append("<td>n/a</td>").append("\n");
		expectedResult.append("<td>0</td>").append("\n");
		expectedResult.append("<td>0</td>").append("\n");
		expectedResult.append("<td>0</td>").append("\n");
		expectedResult.append("</tr>").append("\n");
		expectedResult.append("</tbody>").append("\n");
		expectedResult.append("</table>").append("\n");
		expectedResult.append("</body>").append("\n");
		expectedResult.append("</html>").append("\n");

		assertEquals(expectedResult.toString(), outputWriter.toString());
	}

	public void test_1Class_OtherPackageRequested() {

		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("ingoreExternalTemplates", new Object());

		ClassListReporter ctt = new ClassListReporter(context);

		MutationRun report = new MutationRun();

		{
			ClassUnderTest classUnderTest = new ClassUnderTest();
			classUnderTest.setClassName("MyClass");
			classUnderTest.setPackageName("package1");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(42);
			ratio.setSurvivorCount(21);

			classUnderTest.setMutationRatio(ratio);

			report.getClassUnderTest().add(classUnderTest);
		}

		Writer outputWriter = new StringWriter();

		ctt.generateReport(report, "package2", outputWriter);

		StringBuffer expectedResult = new StringBuffer();

		expectedResult.append("<html>").append("\n");
		expectedResult.append("<body>").append("\n");
		expectedResult.append("<table>").append("\n");
		expectedResult.append("<thead>").append("\n");
		expectedResult.append("<tr>").append("\n");
		expectedResult.append("<td>Class</td>").append("\n");
		expectedResult.append("<td>Coverage Level</td>").append("\n");
		expectedResult.append("<td>Mutation Count</td>").append("\n");
		expectedResult.append("<td>Survived Mutations Count</td>").append("\n");
		expectedResult.append("<td>Defeated Mutations Count</td>").append("\n");
		expectedResult.append("</tr>").append("\n");
		expectedResult.append("</thead>").append("\n");
		expectedResult.append("<tbody>").append("\n");
		expectedResult.append("</tbody>").append("\n");
		expectedResult.append("</table>").append("\n");
		expectedResult.append("</body>").append("\n");
		expectedResult.append("</html>").append("\n");

		assertEquals(expectedResult.toString(), outputWriter.toString());
	}

	public void test_2Classes() {

		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("ingoreExternalTemplates", new Object());

		ClassListReporter ctt = new ClassListReporter(context);

		MutationRun report = new MutationRun();

		{
			ClassUnderTest classUnderTest = new ClassUnderTest();
			classUnderTest.setClassName("MyClass1");
			classUnderTest.setPackageName("package1");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(42);
			ratio.setSurvivorCount(21);

			classUnderTest.setMutationRatio(ratio);

			report.getClassUnderTest().add(classUnderTest);
		}

		{
			ClassUnderTest classUnderTest = new ClassUnderTest();
			classUnderTest.setClassName("MyClass2");
			classUnderTest.setPackageName("package1");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(1);
			ratio.setSurvivorCount(1);

			classUnderTest.setMutationRatio(ratio);

			report.getClassUnderTest().add(classUnderTest);
		}

		{
			ClassUnderTest classUnderTest = new ClassUnderTest();
			classUnderTest.setClassName("MyClass3");
			classUnderTest.setPackageName("package1");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(1);
			ratio.setSurvivorCount(0);

			classUnderTest.setMutationRatio(ratio);

			report.getClassUnderTest().add(classUnderTest);
		}

		Writer outputWriter = new StringWriter();

		ctt.generateReport(report, "package1", outputWriter);

		StringBuffer expectedResult = new StringBuffer();

		expectedResult.append("<html>").append("\n");
		expectedResult.append("<body>").append("\n");
		expectedResult.append("<table>").append("\n");
		expectedResult.append("<thead>").append("\n");
		expectedResult.append("<tr>").append("\n");
		expectedResult.append("<td>Class</td>").append("\n");
		expectedResult.append("<td>Coverage Level</td>").append("\n");
		expectedResult.append("<td>Mutation Count</td>").append("\n");
		expectedResult.append("<td>Survived Mutations Count</td>").append("\n");
		expectedResult.append("<td>Defeated Mutations Count</td>").append("\n");
		expectedResult.append("</tr>").append("\n");
		expectedResult.append("</thead>").append("\n");
		expectedResult.append("<tbody>").append("\n");
		expectedResult.append("<tr>").append("\n");
		expectedResult.append("<td><a href=\"package1.MyClass1.html\">MyClass1</a></td>").append("\n");
		expectedResult.append("<td>50 %</td>").append("\n");
		expectedResult.append("<td>42</td>").append("\n");
		expectedResult.append("<td>21</td>").append("\n");
		expectedResult.append("<td>21</td>").append("\n");
		expectedResult.append("</tr>").append("\n");
		expectedResult.append("<tr>").append("\n");
		expectedResult.append("<td><a href=\"package1.MyClass2.html\">MyClass2</a></td>").append("\n");
		expectedResult.append("<td>0 %</td>").append("\n");
		expectedResult.append("<td>1</td>").append("\n");
		expectedResult.append("<td>1</td>").append("\n");
		expectedResult.append("<td>0</td>").append("\n");
		expectedResult.append("</tr>").append("\n");
		expectedResult.append("<tr>").append("\n");
		expectedResult.append("<td><a href=\"package1.MyClass3.html\">MyClass3</a></td>").append("\n");
		expectedResult.append("<td>100 %</td>").append("\n");
		expectedResult.append("<td>1</td>").append("\n");
		expectedResult.append("<td>0</td>").append("\n");
		expectedResult.append("<td>1</td>").append("\n");
		expectedResult.append("</tr>").append("\n");
		expectedResult.append("</tbody>").append("\n");
		expectedResult.append("</table>").append("\n");
		expectedResult.append("</body>").append("\n");
		expectedResult.append("</html>").append("\n");

		assertEquals(expectedResult.toString(), outputWriter.toString());
	}
}
