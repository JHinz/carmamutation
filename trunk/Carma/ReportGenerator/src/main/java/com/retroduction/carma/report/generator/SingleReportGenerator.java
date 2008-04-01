/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.retroduction.carma.report.generator.html.coverage.CoverageReport;
import com.retroduction.carma.report.generator.util.ProjectBuilder;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.MutationRun;
import com.retroduction.carma.xmlreport.utilities.ReportModelLoader;

public class SingleReportGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length < 3) {
			System.out.println("Use arguments: reportInputFile, outputDirectory, sourceFolder1, sourceFolder2, ...");
			System.exit(-1);
		}

		System.out.println("Start Report generation.");
		try {

			List<File> sourceFolders = new ArrayList<File>();
			for (int i = 2; i < args.length; i++) {
				sourceFolders.add(new File(args[i]));
			}
			File reportFile = new File(args[0]);
			File outputDirectory = new File(args[1]);
			new SingleReportGenerator().perform(new ReportModelLoader().loadReportModel(reportFile), outputDirectory,
					sourceFolders);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Finished Report generation.");

	}

	public void perform(MutationRun report, File outputDirectory, List<File> sourceFolders)
			throws ReportGeneratorException {

		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("/com/retroduction/carma/report/generator/config.xml");
			
			ProjectBuilder builder = new ProjectBuilder();
			Project project = builder.buildProject(sourceFolders);
		
				if (!(outputDirectory.exists())){
					outputDirectory.mkdirs();
					
				}
				CoverageReport coverageReport = (CoverageReport) ctx.getBean("coverageReport");
				coverageReport.generateReport(report, project, outputDirectory);
			
		} catch (Exception e) {
			throw new ReportGeneratorException("Report Generation Failed: " + e.getMessage(), e);
		}

	}

}
