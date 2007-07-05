package com.retroduction.carma.report.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import com.retroduction.carma.report.generator.html.IHTMLReport;
import com.retroduction.carma.report.generator.html.VelocityRenderer;
import com.retroduction.carma.report.generator.html.coverage.CoverageReport;
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
//			StyleSheetProvider cssProvider = new StyleSheetProvider(new File(outputDirectory, "css"));
//			cssProvider.provideStyleSheet("main.css");
//			cssProvider.provideStyleSheet("source-viewer.css");
			ProjectBuilder builder = new ProjectBuilder();
			Project project = builder.buildProject(sourceFolders);

			List<IHTMLReport> reports = new ArrayList<IHTMLReport>();
//			reports.add(new IndexReport());
//			reports.add(new OverviewReport());
//			reports.add(new ClassReport());
			reports.add(new CoverageReport());
			
			
			VelocityRenderer velo = new VelocityRenderer();
			Properties props = new Properties();
			props.setProperty("resource.loader", "class");
			props.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
			props.setProperty("class.resource.loader.class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			velo.init(props);


			if (!(outputDirectory.exists()))
				outputDirectory.mkdirs();

			
			//generate all reports
			for(IHTMLReport r : reports){
				System.out.println("Generating report: \"" +r.getTitle() +"\"");
				r.generateReport(report, project, outputDirectory, velo);
			}
		} catch (Exception e) {
			throw new ReportGeneratorException("Report Generation Failed: " + e.getMessage(), e);
		}

	}

}
