package com.mutation.report.html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.mutation.report.generator.chart.ClassRange;
import com.mutation.report.generator.chart.CoverageBarChartCreator;
import com.mutation.report.generator.reportobjects.ClassInfo;
import com.mutation.report.generator.reportobjects.ClassInfoCreator;
import com.mutation.report.om.MutationRun;
import com.mutation.report.source.om.Project;

/**
 * Creates a project overview report with charts and links to the class details reports
 * @author mike
 *
 */
public class OverviewReport implements IHTMLReport {
	private String templateName = "com/mutation/report/generator/details.vm";

	/* (non-Javadoc)
	 * @see com.mutation.report.html.IHTMLReport#generateReport(com.mutation.report.om.MutationRun, com.mutation.report.source.om.Project, java.io.File, com.mutation.report.html.VelocityRenderer)
	 */
	public void generateReport(MutationRun report, Project project, File outputDirectory, VelocityRenderer renderer)
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
		
		StringWriter w = new StringWriter();

		renderer.render(templateName, vcontext, w);

		FileWriter writer = new FileWriter(new File(outputDirectory, "details.html"));

		writer.write(w.toString());
		writer.close();
	}

	public String getTtitle() {
		return "Overview";
	}
}
