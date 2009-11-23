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

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.retroduction.carma.reportgenerator.FreeMarkerRenderer;
import org.retroduction.carma.reportgenerator.RendererException;
import org.retroduction.carma.reportgenerator.beans.ClassLevelCoverage;

import com.retroduction.carma.utilities.ClassNameUtil;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRun;

import freemarker.template.Configuration;

/**
 * @author arau
 * 
 */
public class ClassListReporter {

	private HashMap<String, Object> context;

	public ClassListReporter() {
		super();
		this.context = new HashMap<String, Object>();
	}

	public ClassListReporter(HashMap<String, Object> context) {
		super();
		this.context = context;
	}

	public void generateReport(MutationRun report, String fqPackageName, Writer outputWriter) throws RendererException {

		List<ClassLevelCoverage> classesUnderTest = new ArrayList<ClassLevelCoverage>();

		for (ClassUnderTest classUnderTest : report.getClassUnderTest()) {

			if (!classUnderTest.getPackageName().equalsIgnoreCase(fqPackageName))
				continue;

			String fqClassName = ClassNameUtil
					.getFqName(classUnderTest.getPackageName(), classUnderTest.getClassName());

			ClassLevelCoverage info = new ClassLevelCoverage(fqClassName, classUnderTest.getClassName(),
					(classUnderTest.getMutationRatio().getMutationCount()), (classUnderTest.getMutationRatio()
							.getSurvivorCount()));

			info.getNumberOfTests(classUnderTest.getExecutedTests().size());

			classesUnderTest.add(info);
		}

		Collections.sort(classesUnderTest);

		context.put("classesUnderTest", classesUnderTest);
		context.put("showPackageBreadCrumb", new Object());
		context.put("packageName", fqPackageName);
		
		FreeMarkerRenderer renderer = new FreeMarkerRenderer("classReport/classlisting.ftl", "/templates/");
		renderer.setConfig(new Configuration());

		renderer.render(context, outputWriter);

	}
}
