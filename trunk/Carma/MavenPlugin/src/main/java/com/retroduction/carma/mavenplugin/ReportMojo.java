/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.mavenplugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import com.retroduction.carma.report.generator.SingleReportGenerator;
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
	 * @parameter expression="${mutation.reportFile}"
	 *            default-value="${project.reporting.outputDirectory}/mutationtest/mutationTestReport.xml"
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
		} catch (FileNotFoundException e) {
			
			log.error(e);
			throw new MojoFailureException("Report File not found");
		} catch (JAXBException e) {
			log.error(e);
			throw new MojoFailureException("Invalid Report File");
		}
		SingleReportGenerator reportGenerator = new SingleReportGenerator();
		
		List<File> sourceDirectories = new ArrayList<File>();
		sourceDirectories.add(this.sourceDir);
		reportGenerator.perform(mutationRun, this.outputDirectory, sourceDirectories);
		
		log.info("# --------------------------------------------------------------------------------");
		log.info("# Mutation Site report generated. Output directory: " +this.outputDirectory);
		log.info("# --------------------------------------------------------------------------------");

	}
}
