package com.retroduction.carma.report.generator.html.coverage;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.retroduction.carma.report.generator.IRenderer;
import com.retroduction.carma.report.generator.chart.CoverageBarChartCreator;
import com.retroduction.carma.report.generator.html.RenderException;
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
	private String templateName = HTMLFILE;
	
	private CoverageBarChartCreator chartCreator;
	
	
	/* (non-Javadoc)
	 * @see com.mutation.report.html.IHTMLReport#generateReport(com.mutation.report.om.MutationRun, com.mutation.report.source.om.Project, java.io.File, com.mutation.report.html.VelocityRenderer)
	 */
	public void generateReport(MutationRun report, Project project,  IRenderer renderer)
			throws RenderException {
		
		
		
		String coverageChartFile =  "coverageChart.png";

		ClassInfoCreator infoCreator = new ClassInfoCreator(report.getClassUnderTest());
		List<ClassInfo> infos = infoCreator.createClassInfos();

		this.chartCreator.createChart(infos, coverageChartFile, "Project Coverage Ratio");
		
		Map<String, Object> vcontext = new HashMap<String, Object>();
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		vcontext.put("chartWidth", this.chartCreator.getWidth());
		vcontext.put("chartHeight", this.chartCreator.getHeight());
		vcontext.put("numberFormat", numberFormat);
		vcontext.put("report", report);
		vcontext.put("project", project);
		vcontext.put("classInfos", infoCreator.createClassInfos());
		
		renderer.render(this.templateName, vcontext, HTMLFILE);
	}


	public void setChartCreator(CoverageBarChartCreator chartCreator) {
		this.chartCreator = chartCreator;
	}
}
