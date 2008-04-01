/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.html.coverage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.retroduction.carma.report.generator.FreeMarkerRenderer;
import com.retroduction.carma.report.generator.html.RenderException;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.MutationRun;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public class CoverageReport {
	private String templatePath = "/com/retroduction/carma/report/generator/html/coverage/";

	private List<ICoverageReport> reports;

	private List<StyleSheetProvider> resourceProviders;

	public void setResourceProviders(List<StyleSheetProvider> resourceProviders) {
		this.resourceProviders = resourceProviders;
	}

	public CoverageReport() {

	}

	public void generateReport(MutationRun report, Project project, File outputDirectory) throws IOException,
			RenderException {

		for (StyleSheetProvider p : resourceProviders) {
			p.provideResources(outputDirectory);
		}

		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(getClass(), templatePath);
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		FreeMarkerRenderer frenderer = new FreeMarkerRenderer();
		frenderer.setConfig(cfg);
		frenderer.setOutputBaseDir(outputDirectory);

		for (ICoverageReport r : reports) {
			r.generateReport(report, project, frenderer);
		}

	}

	public void setReports(List<ICoverageReport> reports) {
		this.reports = reports;
	}

}
