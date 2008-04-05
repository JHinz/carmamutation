/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.html;

import java.io.File;
import java.io.IOException;

import com.retroduction.carma.report.generator.IRenderer;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * interface for creating an html report for a test run
 * 
 * @author mike
 * 
 */
public interface IHTMLReport {

	/**
	 * get the name of the report
	 * @return title or name of the report
	 */
	String getTitle();
	/**
	 * generate an HTML  report
	 * @param report the test report
	 * @param project the project input information
	 * @param outputDirectory base directory for the ouztput
	 * @param renderer the template engine used to render the report
	 * @throws IOException
	 * @throws RenderException
	 */
	void generateReport(MutationRun report, Project project, File outputDirectory, IRenderer renderer)
			throws IOException, RenderException;

}