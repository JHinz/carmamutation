package com.mutation.report.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.mutation.report.generator.chart.MutationCoverateClassBarChartGreator;
import com.mutation.report.generator.reportobjects.ClassInfo;
import com.mutation.report.generator.reportobjects.ClassInfoCreator;
import com.mutation.report.loader.ReportModelLoader;
import com.mutation.report.om.ClassUnderTest;
import com.mutation.report.om.MutationRun;
import com.mutation.report.source.om.Project;
import com.mutation.report.source.om.SourceFile;

public class SingleReportGenerator {

	private String classPathTemplateName = "com/mutation/report/generator/class.vm";

	private String reportIndexTemplateName = "com/mutation/report/generator/report.vm";

	private String reportIndexFileName = "report.html";

	private static final String CONTEXT_REPORT_KEY = "report";

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
			
	
			VelocityEngine velo = new VelocityEngine();
			Properties props = new Properties();
			props.setProperty("resource.loader", "class");
			props.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
			props.setProperty("class.resource.loader.class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			velo.init(props);

			ProjectBuilder builder = new ProjectBuilder();

			Project project = builder.buildProject(sourceFolders);

			if (!(outputDirectory.exists()))
				outputDirectory.mkdirs();

			File coverageChartFile = new File(outputDirectory, "coverageChart.png");
			generateCoverageChart(report, coverageChartFile);
			generateIndexReport(report, outputDirectory, velo, project);
			generateDetailsReport(report, outputDirectory, velo, project);
			generateSingleClassReports(report, outputDirectory, velo, project);
		} catch (Exception e) {
			throw new ReportGeneratorException("Report Generation Failed: " + e.getMessage(), e);
		}

	}

	private void generateCoverageChart(MutationRun report, File chartPngFile) {
		ClassInfoCreator infoCreator = new ClassInfoCreator(report.getClassUnderTest());
		List<ClassInfo> infos = infoCreator.createClassInfos();
		MutationCoverateClassBarChartGreator chartCreator = new MutationCoverateClassBarChartGreator();
		chartCreator.createChart(infos, chartPngFile);
		
		
	}

	private void generateSingleClassReports(MutationRun report, File outputDirectory, VelocityEngine velo,
			Project project) throws Exception, IOException {
		for (ClassUnderTest clazz : report.getClassUnderTest()) {
			VelocityContext vcontext = new VelocityContext();
			vcontext.put("class", clazz);

			SourceFile sourceFile = project.getSourceFile(clazz.getPackageName(), clazz.getClassName());

			if (sourceFile == null) {
				System.out.println("Source Not Found for: " + clazz.getPackageName() + "." + clazz.getClassName()
						+ " classFile: " + clazz.getBaseClassFile() + " sourceFile: " + clazz.getBaseSourceFile());
				continue;
			}
			SourceInfoCreator infoCreator = new SourceInfoCreator(clazz, sourceFile);
			vcontext.put("sourceInfo", infoCreator.createSourceInfo());

			StringWriter w = new StringWriter();

			velo.mergeTemplate(classPathTemplateName, "UTF-8", vcontext, w);

			File reportFile = new File(outputDirectory, clazz.getPackageName() + "." + clazz.getClassName() + ".html");
			FileWriter writer = new FileWriter(reportFile);

			writer.write(w.toString());
			writer.close();
		}
	}

	private void generateIndexReport(MutationRun report, File outputDirectory, VelocityEngine velo, Project project)
			throws Exception, IOException {
		VelocityContext vcontext = new VelocityContext();
		vcontext.put(CONTEXT_REPORT_KEY, report);
		vcontext.put("project", project);

		StringWriter w = new StringWriter();

		velo.mergeTemplate(reportIndexTemplateName, "UTF-8", vcontext, w);

		FileWriter writer = new FileWriter(new File(outputDirectory, reportIndexFileName));
		writer.write(w.toString());
		writer.close();
	}

	private void generateDetailsReport(MutationRun report, File outputDirectory, VelocityEngine velo, Project project)
			throws Exception, IOException {
		VelocityContext vcontext = new VelocityContext();
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		vcontext.put("numberFormat", numberFormat);
		vcontext.put(CONTEXT_REPORT_KEY, report);
		vcontext.put("project", project);
		ClassInfoCreator infoCreator = new ClassInfoCreator(report.getClassUnderTest());
		vcontext.put("classInfos", infoCreator.createClassInfos());

		StringWriter w = new StringWriter();

		velo.mergeTemplate("com/mutation/report/generator/details.vm", "UTF-8", vcontext, w);

		FileWriter writer = new FileWriter(new File(outputDirectory, "details.html"));

		writer.write(w.toString());
		writer.close();
	}

}
