package com.mutation.report.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import com.mutation.report.loader.ReportModelLoader;
import com.mutation.report.om.MutationRun;

public class SingleReportGenerator {

	private static final String REPORT_INDEX_TEMPLATE = "report.vm";

	private static final String REPORT_INDEX_PAGE = "report.html";

	private static final String CONTEXT_REPORT_KEY = "report";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 3) {
			System.out.println("Use arguments: reportInputFile, outputDirectory, templateDirectory");
			System.exit(-1);
		}

		try {
			new SingleReportGenerator().perform(new ReportModelLoader().loadReportModel(args[0]), args[1], args[2]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void perform(MutationRun report, String outputDirectory, String templateFileDirectory) throws Exception {

		VelocityEngine engine = new VelocityEngine();
		engine.init();

		if (!(new File(outputDirectory).exists()))
			new File(outputDirectory).mkdir();

		VelocityContext vcontext = new VelocityContext();
		vcontext.put(CONTEXT_REPORT_KEY, report);

		StringWriter w = new StringWriter();

		Velocity.mergeTemplate(templateFileDirectory + "/" + REPORT_INDEX_TEMPLATE, "UTF-8", vcontext, w);

		FileWriter writer = new FileWriter(outputDirectory + "/" + REPORT_INDEX_PAGE);
		writer.write(w.toString());
		writer.close();

	}

}
