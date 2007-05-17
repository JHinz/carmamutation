package com.mutation.report.html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;

import com.mutation.report.generator.SourceInfoCreator;
import com.mutation.report.om.ClassUnderTest;
import com.mutation.report.om.MutationRun;
import com.mutation.report.source.om.Project;
import com.mutation.report.source.om.SourceFile;

/**
 * creates a report for a single class
 * @author mike
 *
 */
public class ClassReport implements IHTMLReport {
	private String templateName = "com/mutation/report/generator/class.vm";

	/* (non-Javadoc)
	 * @see com.mutation.report.html.IHTMLReport#generateReport(com.mutation.report.om.MutationRun, com.mutation.report.source.om.Project, java.io.File, com.mutation.report.html.VelocityRenderer)
	 */
	public void generateReport(MutationRun report, Project project, File outputDirectory, VelocityRenderer renderer)
			throws IOException, RenderException {
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

			renderer.render(templateName, vcontext, w);

			File reportFile = new File(outputDirectory, clazz.getPackageName() + "." + clazz.getClassName() + ".html");
			FileWriter writer = new FileWriter(reportFile);

			writer.write(w.toString());
			writer.close();
		}
	}

	public String getTtitle() {
		return "Detailed Class Reports";
	}
}
