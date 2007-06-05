package com.mutation.report.generator.html.coverage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mutation.report.generator.html.IHTMLReport;
import com.mutation.report.generator.html.IRenderer;
import com.mutation.report.generator.html.OverviewReport;
import com.mutation.report.generator.html.RenderException;
import com.mutation.report.om.MutationRun;
import com.mutation.report.source.om.Project;

public class CoverageReport implements IHTMLReport {
	private List<ICoverageReport> subReports;
	
	
	public CoverageReport(){
		subReports = new ArrayList<ICoverageReport>();
		subReports.add(new IndexReport());
		subReports.add(new PackageSummaryReport());
		subReports.add(new OverviewReport());
		subReports.add(new ClassListReport());
		subReports.add(new PackagesReport());
		subReports.add(new ClassReport());
	}
	public void generateReport(MutationRun report, Project project, File outputDirectory, IRenderer renderer)
			throws IOException, RenderException {
		StyleSheetProvider cssProvider = new StyleSheetProvider(new File(outputDirectory, "css"));
		cssProvider.provideStyleSheet("main.css");
		cssProvider.provideStyleSheet("source-viewer.css");

		for(ICoverageReport r : subReports){
			r.generateReport(report, project, outputDirectory, renderer);
		}
		
	}

	public String getTitle() {
		List<String> subReportNames = new ArrayList<String>();
		for(IHTMLReport r : subReports){
			subReportNames.add(r.getTitle());
		}
		return "CoverageReport: " +subReportNames;
	}

}
