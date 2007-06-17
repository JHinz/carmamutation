package com.retroduction.carma.report.generator.html.coverage;

import java.io.File;
import java.io.IOException;

import com.retroduction.carma.report.generator.html.IRenderer;
import com.retroduction.carma.report.generator.html.RenderException;
import com.retroduction.carma.report.generator.html.SingleClassReportCreator;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.report.om.SourceFile;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * creates a report for a single class
 * 
 * @author mike
 * 
 */
public class ClassReport implements ICoverageReport {
	private SingleClassReportCreator reportCreator = new SingleClassReportCreator();

	private String templateName = TEMPLATEPATH +"class.html"; 

	public static String calcHtmlFileName(String packageName, String className){
		return packageName + "." + className + ".html";
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mutation.report.html.IHTMLReport#generateReport(com.mutation.report.om.MutationRun,
	 *      com.mutation.report.source.om.Project, java.io.File,
	 *      com.mutation.report.html.VelocityRenderer)
	 */
	public void generateReport(MutationRun report, Project project, File outputDirectory, IRenderer renderer)
			throws IOException, RenderException {
		for (ClassUnderTest clazz : report.getClassUnderTest()) {
			SourceFile sourceFile = project.getSourceFile(clazz.getPackageName(), clazz.getClassName());

			if (sourceFile == null) {
				System.out.println("Source Not Found for: " + clazz.getPackageName() + "." + clazz.getClassName() 
						+ " classFile: " + clazz.getBaseClassFile() + " sourceFile: " + clazz.getBaseSourceFile()); 
				continue;
			}

			File reportFile = new File(outputDirectory, calcHtmlFileName(clazz.getPackageName(), clazz.getClassName())); 
			reportCreator.createClassReport(clazz, sourceFile, reportFile, templateName, renderer);

		}
	}

	public String getTitle() {
		return "Detailed Class Reports"; 
	}

	public void setReportCreator(SingleClassReportCreator reportCreator) {
		this.reportCreator = reportCreator;
	}

}
