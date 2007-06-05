package com.mutation.report.generator.html;

import java.io.File;
import java.io.IOException;

import org.apache.velocity.VelocityContext;

import com.mutation.report.generator.SourceInfoCreator;
import com.mutation.report.om.ClassUnderTest;
import com.mutation.report.source.om.SourceFile;

public class SingleClassReportCreator {

	public static final String TRANSITIONS_DESCRIPTION = "transitionsDescription";
	/**
	 * list of SourceLineInfo  context variable key
	 */
	public static final String SOURCE_INFO = "sourceInfo";
	/**
	 * ClassUnderTest context variable key
	 */
	public static final String CLASS = "class";

	/**
	 * creates a report for a signle class
	 * @param clazz the class information
	 * @param sourceFile source information
	 * @param reportFile file to write the report to
	 * @param renderer thge renderer
	 * @throws IOException
	 */
	public void createClassReport(ClassUnderTest clazz, SourceFile sourceFile, File reportFile, String templateName, IRenderer renderer) throws IOException {
		VelocityContext vcontext = new VelocityContext();
		vcontext.put(CLASS, clazz);
		SourceInfoCreator infoCreator = new SourceInfoCreator(clazz, sourceFile);
		vcontext.put(SOURCE_INFO, infoCreator.createSourceInfo());
		
		vcontext.put(TRANSITIONS_DESCRIPTION, new TransitionDescription() );

		renderer.render(templateName, vcontext, reportFile);
	}
}
