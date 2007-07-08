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

public class CoverageReport  {
	private String templatePath = "/com/retroduction/carma/report/generator/html/coverage/";

	private List<ICoverageReport> reports;
	
	public CoverageReport(){

	}
	public void generateReport(MutationRun report, Project project, File outputDirectory)
			throws IOException, RenderException {
		StyleSheetProvider cssProvider = new StyleSheetProvider(new File(outputDirectory, "css"));
		cssProvider.provideStyleSheet("main.css");
		cssProvider.provideStyleSheet("source-viewer.css");
		
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(this.getClass(), this.templatePath);
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		FreeMarkerRenderer frenderer = new FreeMarkerRenderer();
		frenderer.setConfig(cfg);
		frenderer.setOutputBaseDir(outputDirectory);

		for(ICoverageReport r : this.reports){
			r.generateReport(report, project, frenderer);
		}
		
	}

	public void setReports(List<ICoverageReport> reports) {
		this.reports = reports;
	}

}
