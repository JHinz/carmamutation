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
import java.io.Writer;
import java.util.HashMap;

import junit.framework.TestCase;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRatio;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * @author arau
 * 
 */
public class ClassListReporterTestCase extends TestCase {

	private final String EOF_CHAR = System.getProperty("line.separator");

	public void test_NoClasses() {

		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("ingoreExternalTemplates", new Object());

		ClassListReporter ctt = new ClassListReporter(context);

		MutationRun report = new MutationRun();

		Writer outputWriter = new StringWriter();

		ctt.generateReport(report, "package1", outputWriter);

		StringBuffer expectedResult = new StringBuffer();

		expectedResult.append("<html>").append(EOF_CHAR);
		expectedResult.append("<body>").append(EOF_CHAR);
		expectedResult.append("<div class=\"classListing\">").append(EOF_CHAR);
		expectedResult.append("<table>").append(EOF_CHAR);
		expectedResult.append("<thead>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>Class</td>").append(EOF_CHAR);
		expectedResult.append("<td>Coverage Level</td>").append(EOF_CHAR);
		expectedResult.append("<td>Mutation Count</td>").append(EOF_CHAR);
		expectedResult.append("<td>Survived Mutations Count</td>").append(EOF_CHAR);
		expectedResult.append("<td>Defeated Mutations Count</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</thead>").append(EOF_CHAR);
		expectedResult.append("<tbody>").append(EOF_CHAR);
		expectedResult.append("</tbody>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</body>").append(EOF_CHAR);
		expectedResult.append("</html>").append(EOF_CHAR);

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

		expectedResult.append("<html>").append(EOF_CHAR);
		expectedResult.append("<body>").append(EOF_CHAR);
		expectedResult.append("<div class=\"classListing\">").append(EOF_CHAR);
		expectedResult.append("<table>").append(EOF_CHAR);
		expectedResult.append("<thead>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>Class</td>").append(EOF_CHAR);
		expectedResult.append("<td>Coverage Level</td>").append(EOF_CHAR);
		expectedResult.append("<td>Mutation Count</td>").append(EOF_CHAR);
		expectedResult.append("<td>Survived Mutations Count</td>").append(EOF_CHAR);
		expectedResult.append("<td>Defeated Mutations Count</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</thead>").append(EOF_CHAR);
		expectedResult.append("<tbody>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td><a href=\"package1.MyClass.html\">MyClass</a></td>").append(EOF_CHAR);
		expectedResult.append("<td>50 %</td>").append(EOF_CHAR);
		expectedResult.append("<td>42</td>").append(EOF_CHAR);
		expectedResult.append("<td>21</td>").append(EOF_CHAR);
		expectedResult.append("<td>21</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</tbody>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</body>").append(EOF_CHAR);
		expectedResult.append("</html>").append(EOF_CHAR);

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

		expectedResult.append("<html>").append(EOF_CHAR);
		expectedResult.append("<body>").append(EOF_CHAR);
		expectedResult.append("<div class=\"classListing\">").append(EOF_CHAR);
		expectedResult.append("<table>").append(EOF_CHAR);
		expectedResult.append("<thead>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>Class</td>").append(EOF_CHAR);
		expectedResult.append("<td>Coverage Level</td>").append(EOF_CHAR);
		expectedResult.append("<td>Mutation Count</td>").append(EOF_CHAR);
		expectedResult.append("<td>Survived Mutations Count</td>").append(EOF_CHAR);
		expectedResult.append("<td>Defeated Mutations Count</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</thead>").append(EOF_CHAR);
		expectedResult.append("<tbody>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td><a href=\"package1.MyClass.html\">MyClass</a></td>").append(EOF_CHAR);
		expectedResult.append("<td>n/a</td>").append(EOF_CHAR);
		expectedResult.append("<td>0</td>").append(EOF_CHAR);
		expectedResult.append("<td>0</td>").append(EOF_CHAR);
		expectedResult.append("<td>0</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</tbody>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</body>").append(EOF_CHAR);
		expectedResult.append("</html>").append(EOF_CHAR);

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

		expectedResult.append("<html>").append(EOF_CHAR);
		expectedResult.append("<body>").append(EOF_CHAR);
		expectedResult.append("<div class=\"classListing\">").append(EOF_CHAR);
		expectedResult.append("<table>").append(EOF_CHAR);
		expectedResult.append("<thead>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>Class</td>").append(EOF_CHAR);
		expectedResult.append("<td>Coverage Level</td>").append(EOF_CHAR);
		expectedResult.append("<td>Mutation Count</td>").append(EOF_CHAR);
		expectedResult.append("<td>Survived Mutations Count</td>").append(EOF_CHAR);
		expectedResult.append("<td>Defeated Mutations Count</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</thead>").append(EOF_CHAR);
		expectedResult.append("<tbody>").append(EOF_CHAR);
		expectedResult.append("</tbody>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</body>").append(EOF_CHAR);
		expectedResult.append("</html>").append(EOF_CHAR);

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

		expectedResult.append("<html>").append(EOF_CHAR);
		expectedResult.append("<body>").append(EOF_CHAR);
		expectedResult.append("<div class=\"classListing\">").append(EOF_CHAR);
		expectedResult.append("<table>").append(EOF_CHAR);
		expectedResult.append("<thead>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>Class</td>").append(EOF_CHAR);
		expectedResult.append("<td>Coverage Level</td>").append(EOF_CHAR);
		expectedResult.append("<td>Mutation Count</td>").append(EOF_CHAR);
		expectedResult.append("<td>Survived Mutations Count</td>").append(EOF_CHAR);
		expectedResult.append("<td>Defeated Mutations Count</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</thead>").append(EOF_CHAR);
		expectedResult.append("<tbody>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td><a href=\"package1.MyClass1.html\">MyClass1</a></td>").append(EOF_CHAR);
		expectedResult.append("<td>50 %</td>").append(EOF_CHAR);
		expectedResult.append("<td>42</td>").append(EOF_CHAR);
		expectedResult.append("<td>21</td>").append(EOF_CHAR);
		expectedResult.append("<td>21</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td><a href=\"package1.MyClass2.html\">MyClass2</a></td>").append(EOF_CHAR);
		expectedResult.append("<td>0 %</td>").append(EOF_CHAR);
		expectedResult.append("<td>1</td>").append(EOF_CHAR);
		expectedResult.append("<td>1</td>").append(EOF_CHAR);
		expectedResult.append("<td>0</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td><a href=\"package1.MyClass3.html\">MyClass3</a></td>").append(EOF_CHAR);
		expectedResult.append("<td>100 %</td>").append(EOF_CHAR);
		expectedResult.append("<td>1</td>").append(EOF_CHAR);
		expectedResult.append("<td>0</td>").append(EOF_CHAR);
		expectedResult.append("<td>1</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</tbody>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</body>").append(EOF_CHAR);
		expectedResult.append("</html>").append(EOF_CHAR);

		assertEquals(expectedResult.toString(), outputWriter.toString());
	}
}
