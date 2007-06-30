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
	private File sourceDir;

	/**
	 * The location for the mutation report
	 */
	private File reportFile;

	/**
	 * The target location for the mutation site report
	 */
	private File outputDirectory;

	public void setSourceDir(File sourceDir) {
		this.sourceDir = sourceDir;
	}

	public void setReportFile(File reportFile) {
		this.reportFile = reportFile;
	}

	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public void execute() {

		ReportModelLoader loader = new ReportModelLoader();
		MutationRun mutationRun;
		try {
			mutationRun = loader.loadReportModel(this.reportFile);
		} catch (FileNotFoundException e) {
			log(e, 2);
			throw new BuildException("Report File not found");
		} catch (JAXBException e) {
			log(e, 2);
			throw new BuildException("Invalid Report File");
		}
		SingleReportGenerator reportGenerator = new SingleReportGenerator();

		List<File> sourceDirectories = new ArrayList<File>();
		sourceDirectories.add(this.sourceDir);
		reportGenerator.perform(mutationRun, outputDirectory, sourceDirectories);

		log("# --------------------------------------------------------------------------------");
		log("# Mutation Site report generated. Output directory: " + outputDirectory);
		log("# --------------------------------------------------------------------------------");

	}
}
