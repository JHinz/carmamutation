/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */

package com.retroduction.carma.mavenplugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.retroduction.carma.reportgenerator.ReportGenerator;

import com.retroduction.carma.xmlreport.om.MutationRun;
import com.retroduction.carma.xmlreport.utilities.ReportModelLoader;

/**
 * Goal which generates site reports.
 * 
 * @goal mreport
 * 
 * @phase site
 */

public class ReportMojo extends AbstractMojo {

	/**
	 * The location of the generated class files
	 * 
	 * @parameter expression="${mutation.sourceDir}"
	 *            default-value="${project.build.sourceDirectory}"
	 * @required
	 */
	private File sourceDir;

	/**
	 * The location for the mutation report
	 * 
	 * @parameter expression="${mutation.reportFile}" default-value=
	 *            "${project.reporting.outputDirectory}/mutationtest/mutationTestReport.xml"
	 * @required
	 */
	private File reportFile;

	/**
	 * The target location for the mutation site report
	 * 
	 * @parameter expression="${mutation.outputDirectory}"
	 *            default-value="${project.reporting.outputDirectory}/mutationtest/"
	 * @required
	 */
	private File outputDirectory;

	public void execute() throws MojoExecutionException, MojoFailureException {
		Log log = this.getLog();
		ReportModelLoader loader = new ReportModelLoader();
		MutationRun mutationRun;
		try {
			mutationRun = loader.loadReportModel(this.reportFile);
			ReportGenerator reportGenerator = new ReportGenerator();

			List<File> sourceDirectories = new ArrayList<File>();
			sourceDirectories.add(this.sourceDir);
			reportGenerator.perform(mutationRun, this.outputDirectory, sourceDirectories);

			log.info("# --------------------------------------------------------------------------------");
			log.info("# Mutation Site report generated. Output directory: " + this.outputDirectory);
			log.info("# --------------------------------------------------------------------------------");
		} catch (Exception e) {
			log.error(e);
			throw new MojoFailureException("Error during report generation");
		}

	}
}
