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
