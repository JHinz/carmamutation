package com.retroduction.carma.report.generator.html;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.retroduction.carma.report.generator.chart.CoverageBarChartCreator;
import com.retroduction.carma.report.generator.html.coverage.ICoverageReport;
import com.retroduction.carma.report.generator.reportobjects.ClassInfo;
import com.retroduction.carma.report.generator.reportobjects.ClassInfoCreator;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * Creates a project overview report with charts and links to the class details reports
 * @author mike
 *
 */
public class OverviewReport implements ICoverageReport {
	public static final String HTMLFILE = "overview.html";
	private String templateName = TEMPLATEPATH +HTMLFILE;
	
	/* (non-Javadoc)
	 * @see com.mutation.report.html.IHTMLReport#generateReport(com.mutation.report.om.MutationRun, com.mutation.report.source.om.Project, java.io.File, com.mutation.report.html.VelocityRenderer)
	 */
	public void generateReport(MutationRun report, Project project, File outputDirectory, IRenderer renderer)
			throws IOException, RenderException {
		
		
		
		File coverageChartFile = new File(outputDirectory, "coverageChart.png");

		ClassInfoCreator infoCreator = new ClassInfoCreator(report.getClassUnderTest());
		List<ClassInfo> infos = infoCreator.createClassInfos();

		CoverageBarChartCreator chartCreator = new CoverageBarChartCreator();

		chartCreator.createChart(infos, coverageChartFile, "Project Coverage Ratio");
		
		VelocityContext vcontext = new VelocityContext();
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		vcontext.put("chartWidth", chartCreator.getWidth());
		vcontext.put("chartHeight", chartCreator.getHeight());
		vcontext.put("numberFormat", numberFormat);
		vcontext.put("report", report);
		vcontext.put("project", project);
		vcontext.put("classInfos", infoCreator.createClassInfos());
		
		File outputFile = new File(outputDirectory, HTMLFILE);
		renderer.render(templateName, vcontext, outputFile);
	}

	public String getTitle() {
		return "Overview";
	}
}
