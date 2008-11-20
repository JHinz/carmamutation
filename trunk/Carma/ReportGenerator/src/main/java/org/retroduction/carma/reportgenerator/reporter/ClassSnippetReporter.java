/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.reporter;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.retroduction.carma.reportgenerator.FreeMarkerRenderer;
import org.retroduction.carma.reportgenerator.RendererException;
import org.retroduction.carma.reportgenerator.beanbuilder.SnippetBuilder;

import com.retroduction.carma.report.om.SourceFile;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;

import freemarker.template.Configuration;

/**
 * @author arau
 * 
 */
public class ClassSnippetReporter {

	private HashMap<String, Object> context;

	public ClassSnippetReporter() {
		super();
		this.context = new HashMap<String, Object>();
	}

	public ClassSnippetReporter(HashMap<String, Object> context) {
		super();
		this.context = context;
	}

	public void createReport(ClassUnderTest classReport, SourceFile sourceFile, Writer writer, ResourceBundle resources)
			throws RendererException, IOException {

		SnippetBuilder builder = new SnippetBuilder(classReport, sourceFile);

		boolean extendedJCovInfoAvailable = false;

		if (classReport.getMutant().size() > 0 && classReport.getMutant().get(0).getBaseSourceColumnStart() > 0) {
			extendedJCovInfoAvailable = true;
		}

		context.put("snippets", builder.getSnippets());
		context.put("i18nTransition", resources);

		context.put("showClassBreadCrumb", new Object());
		context.put("packageName", classReport.getPackageName());
		context.put("className", classReport.getClassName());

		context.put("extendedJcovInfoAvailable", new Boolean(extendedJCovInfoAvailable));

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("classReport/classReport.ftl", "/templates/");
		renderer.setConfig(new Configuration());

		renderer.render(context, writer);
	}

}
