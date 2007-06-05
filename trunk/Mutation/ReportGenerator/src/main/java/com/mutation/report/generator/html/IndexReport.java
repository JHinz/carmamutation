package com.mutation.report.generator.html;

import java.io.File;
import java.io.IOException;

import org.apache.velocity.VelocityContext;

import com.mutation.report.om.MutationRun;
import com.mutation.report.source.om.Project;


public class IndexReport implements IHTMLReport {
	private String templateName = "com/mutation/report/generator/report.vm";

	/* (non-Javadoc)
	 * @see com.mutation.report.html.IHTMLReport#generateReport(com.mutation.report.om.MutationRun, com.mutation.report.source.om.Project, java.io.File, com.mutation.report.html.VelocityRenderer)
	 */
	public void generateReport(MutationRun report, Project project, File outputDirectory, IRenderer renderer)
			throws IOException, RenderException {
		VelocityContext vcontext = new VelocityContext();
		vcontext.put("report", report);
		vcontext.put("project", project);

		File outputFile =  new File(outputDirectory, "index.html");
		renderer.render(templateName,  vcontext, outputFile);
	}

	public String getTitle() {
		return "Index";
	}
}
