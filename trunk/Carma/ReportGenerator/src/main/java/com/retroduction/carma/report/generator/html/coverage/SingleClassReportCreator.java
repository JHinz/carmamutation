package com.retroduction.carma.report.generator.html.coverage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.retroduction.carma.report.generator.IRenderer;
import com.retroduction.carma.report.generator.RendererException;
import com.retroduction.carma.report.generator.SourceInfoCreator;
import com.retroduction.carma.report.generator.html.TransitionDescription;
import com.retroduction.carma.report.generator.reportobjects.ClassCoverageInfoCreator;
import com.retroduction.carma.report.generator.reportobjects.CoverageInfo;
import com.retroduction.carma.report.om.SourceFile;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;

public class SingleClassReportCreator {

	public static final String TRANSITIONS_DESCRIPTION = "transitionsDescription";

	private ClassCoverageInfoCreator coverageInfoCreator;
	/**
	 * list of SourceLineInfo context variable key
	 */
	public static final String SOURCE_INFO = "sourceInfo";

	/**
	 * ClassUnderTest context variable key
	 */
	public static final String CLASS = "clazz";

	public static final String COVERAGEINFO = "coverage";

	/**
	 * creates a report for a signle class
	 * 
	 * @param clazz
	 *            the class information
	 * @param sourceFile
	 *            source information
	 * @param reportFile
	 *            file to write the report to
	 * @param renderer
	 *            thge renderer
	 * @throws IOException
	 */
	public void createClassReport(ClassUnderTest clazz, SourceFile sourceFile, String reportFileName,
			String templateName, IRenderer renderer) throws RendererException {
		Map<String, Object> ctx = new HashMap<String, Object>();
		

		CoverageInfo info = this.coverageInfoCreator.createCoverageInfo(clazz);
		ctx.put(COVERAGEINFO, info);
		ctx.put(CLASS, clazz);
		SourceInfoCreator infoCreator = new SourceInfoCreator(clazz, sourceFile);
		ctx.put(SOURCE_INFO, infoCreator.createSourceInfo());

		ctx.put(TRANSITIONS_DESCRIPTION, new TransitionDescription());

		renderer.render(templateName, ctx, reportFileName);
	}

	public void setCoverageInfoCreator(ClassCoverageInfoCreator coverageInfoCreator) {
		this.coverageInfoCreator = coverageInfoCreator;
	}
}
