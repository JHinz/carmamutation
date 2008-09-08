/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.ant;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.retroduction.carma.reportgenerator.ReportGenerator;

import com.retroduction.carma.xmlreport.om.MutationRun;
import com.retroduction.carma.xmlreport.utilities.ReportModelLoader;

/**
 * Target which generates site reports.
 */

public class AntCarmaReport extends org.apache.tools.ant.Task {

	/**
	 * The location of the generated class files
	 */
	private String sourceDir;

	/**
	 * The location for the mutation report
	 */
	private String reportFile;

	/**
	 * The target location for the mutation site report
	 */
	private String outputDirectory;

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	@Override
	public void execute() {
		ClassLoader threadClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader thisClassLoader = this.getClass().getClassLoader();

		Thread.currentThread().setContextClassLoader(thisClassLoader);
		try {
			ReportModelLoader loader = new ReportModelLoader();

			MutationRun mutationRun;
			try {
				mutationRun = loader.loadReportModel(new File(this.reportFile));
			} catch (FileNotFoundException e) {
				this.log(e, 2);
				throw new BuildException("Report File not found");
			} catch (JAXBException e) {
				this.log(e, 2);
				throw new BuildException("Invalid Report File");
			}
			ReportGenerator reportGenerator = new ReportGenerator();

			List<File> sourceDirectories = new ArrayList<File>();
			sourceDirectories.add(new File(this.sourceDir));
			reportGenerator.perform(mutationRun, new File(this.outputDirectory), sourceDirectories);

			this.log("# --------------------------------------------------------------------------------");
			this.log("# Mutation Site report generated. Output directory: " + this.outputDirectory);
			this.log("# --------------------------------------------------------------------------------");
		} catch (Exception e) {
			this.log(e, Project.MSG_WARN);
			throw new BuildException("Error during writing report.");
		} finally {
			Thread.currentThread().setContextClassLoader(threadClassLoader);
		}
	}
}
