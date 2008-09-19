/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
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
