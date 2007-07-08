package com.retroduction.carma.report.generator.html.coverage;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.retroduction.carma.report.generator.IRenderer;
import com.retroduction.carma.report.generator.html.RenderException;
import com.retroduction.carma.report.generator.reportobjects.ClassCoverageInfoCreator;
import com.retroduction.carma.report.generator.reportobjects.CoverageInfo;
import com.retroduction.carma.report.generator.reportobjects.CoverageInfoAggregator;
import com.retroduction.carma.report.generator.reportobjects.PackageInfo;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.utilities.ClassNameUtil;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * creates package summary, listing all classes within package
 * 
 * @author mike
 * 
 */
public class PackageSummaryReport implements ICoverageReport {
	public static final String HTMLFILE = "summary.html";

	private String templateName = HTMLFILE;

	private DateFormat dateFormat = DateFormat.getDateTimeInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mutation.report.html.IHTMLReport#generateReport(com.mutation.report.om.MutationRun,
	 *      com.mutation.report.source.om.Project, java.io.File,
	 *      com.mutation.report.html.VelocityRenderer)
	 */
	public void generateReport(MutationRun report, Project project, IRenderer renderer) throws RenderException {
		Map<String, Object> ctx = new HashMap<String, Object>();
		PackageInfo packageInfo = new PackageInfo(report.getClassUnderTest());
		this.createPackageReport(report, project, packageInfo, "", HTMLFILE, ctx, renderer);

		for (String packageName : packageInfo.getPackageNames()) {
			String packageOutputFile = "summary-" + packageName + ".html";
			this.createPackageReport(report, project, packageInfo, packageName, packageOutputFile, ctx, renderer);
		}
	}

	private void createPackageReport(MutationRun report, Project project, PackageInfo packageInfo, String basePackageName, String outputFile, Map<String, Object> ctx, IRenderer renderer) {
		ctx.put("packageName", basePackageName);
		// list of packgeinfo for this and all sub packages
		// name, #classesInPackage, coverage, #mutants, #survivors
		CoverageInfoAggregator aggregator = new CoverageInfoAggregator();
		
		Set<String> packageNames = packageInfo.getSubPackages(basePackageName);
		List<CoverageInfo> packages = new ArrayList<CoverageInfo>(packageNames.size());
		for( String packageName : packageNames){
			String shortName = ClassNameUtil.getShortName(packageName); 
			CoverageInfo packageCoverage = aggregator.aggregate(packageName, shortName, packageInfo.getClassesInPackage(packageName));
			packages.add(packageCoverage);
		}
		ctx.put("packages", packages);
		
		
		Set<ClassUnderTest> cutList = packageInfo.getClassesInPackage(basePackageName);
		ClassCoverageInfoCreator classInfoCreator = new ClassCoverageInfoCreator();
		List<CoverageInfo> classes = classInfoCreator.createCoverageInfo(cutList);
		// list of classes in package
		// name, coverage, #mutants, #survivors
		
		ctx.put("classes", classes);

		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);		
		ctx.put("numberFormat", format);
		
		ctx.put("date", this.dateFormat.format(new Date()));
		renderer.render(this.templateName,  ctx, outputFile);
		
	}


}
