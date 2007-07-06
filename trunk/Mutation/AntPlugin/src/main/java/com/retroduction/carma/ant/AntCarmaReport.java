package com.retroduction.carma.ant;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.tools.ant.BuildException;

import com.retroduction.carma.report.generator.SingleReportGenerator;
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

	public void execute() {
		ClassLoader threadClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader thisClassLoader = this.getClass().getClassLoader();

		Thread.currentThread().setContextClassLoader(thisClassLoader);
		try {

			ReportModelLoader loader = new ReportModelLoader();
			MutationRun mutationRun;
			try {
				mutationRun = loader.loadReportModel(new File(reportFile));
			} catch (FileNotFoundException e) {
				log(e, 2);
				throw new BuildException("Report File not found");
			} catch (JAXBException e) {
				log(e, 2);
				throw new BuildException("Invalid Report File");
			}
			SingleReportGenerator reportGenerator = new SingleReportGenerator();

			List<File> sourceDirectories = new ArrayList<File>();
			sourceDirectories.add(new File(sourceDir));
			reportGenerator.perform(mutationRun, new File(outputDirectory), sourceDirectories);

			log("# --------------------------------------------------------------------------------");
			log("# Mutation Site report generated. Output directory: " + outputDirectory);
			log("# --------------------------------------------------------------------------------");
		} finally {
			Thread.currentThread().setContextClassLoader(threadClassLoader);
		}
	}
}
