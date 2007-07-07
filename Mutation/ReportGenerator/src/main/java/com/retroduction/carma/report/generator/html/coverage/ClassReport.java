package com.retroduction.carma.report.generator.html.coverage;

import com.retroduction.carma.report.generator.IRenderer;
import com.retroduction.carma.report.generator.html.RenderException;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.report.om.SourceFile;
import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * creates a report for a single class
 * 
 * @author mike
 * 
 */
public class ClassReport implements ICoverageReport {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private SingleClassReportCreator reportCreator;

	private String templateName = "class.html"; 

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
	public void generateReport(MutationRun report, Project project, IRenderer renderer)
			throws  RenderException {
		for (ClassUnderTest clazz : report.getClassUnderTest()) {
			SourceFile sourceFile = project.getSourceFile(clazz.getPackageName(), clazz.getClassName());

			if (sourceFile == null) {
				logger.warn("Source Not Found for: " + clazz.getPackageName() + "." + clazz.getClassName() 
						+ " classFile: " + clazz.getBaseClassFile() + " sourceFile: " + clazz.getBaseSourceFile()); 
				continue;
			}

			String reportFile = calcHtmlFileName(clazz.getPackageName(), clazz.getClassName()); 
			reportCreator.createClassReport(clazz, sourceFile, reportFile, templateName, renderer);

		}
	}

	public void setReportCreator(SingleClassReportCreator reportCreator) {
		this.reportCreator = reportCreator;
	}

}
