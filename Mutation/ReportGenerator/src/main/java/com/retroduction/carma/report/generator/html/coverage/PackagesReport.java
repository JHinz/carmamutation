package com.retroduction.carma.report.generator.html.coverage;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashSet;

import org.apache.velocity.VelocityContext;

import com.retroduction.carma.report.generator.html.IRenderer;
import com.retroduction.carma.report.generator.html.RenderException;
import com.retroduction.carma.report.generator.reportobjects.PackageInfo;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * creates a report for a single class
 * 
 * @author mike
 * 
 */
public class PackagesReport implements ICoverageReport {
	public static final String HTMLFILE = "packages.html";
	private String templateName = TEMPLATEPATH + HTMLFILE; 
	private DateFormat dateFormat = DateFormat.getDateTimeInstance();

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mutation.report.html.IHTMLReport#generateReport(com.mutation.report.om.MutationRun,
	 *      com.mutation.report.source.om.Project, java.io.File,
	 *      com.mutation.report.html.VelocityRenderer)
	 */
	public void generateReport(MutationRun report, Project project, File outputDirectory, IRenderer renderer)
			throws IOException, RenderException {
		VelocityContext vcontext = new VelocityContext();

		
		
		//TODO conversion between list and set should not be needed (always use set)
		PackageInfo packageInfo = new PackageInfo(new HashSet<ClassUnderTest>(report.getClassUnderTest()));
		File outputFile =  new File(outputDirectory, HTMLFILE);
		createPackageReport(packageInfo, "", outputFile, vcontext, renderer);
		
	}

	private void createPackageReport(PackageInfo packageInfo, String basePackage, File outputFile, VelocityContext vcontext, IRenderer renderer){
		vcontext.put("packageInfo", packageInfo);
		vcontext.put("package", "");
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);		
		vcontext.put("numberFormat", format);
		
		vcontext.put("date", dateFormat.format(new Date()));

		renderer.render(templateName,  vcontext, outputFile);
		
	}
	public String getTitle() {
		return "PackageList Report"; 
	}

}
