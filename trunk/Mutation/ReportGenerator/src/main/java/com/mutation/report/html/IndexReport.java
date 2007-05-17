package com.mutation.report.html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;

import com.mutation.report.om.MutationRun;
import com.mutation.report.source.om.Project;


public class IndexReport implements IHTMLReport {
	private String templateName = "com/mutation/report/generator/report.vm";

	/* (non-Javadoc)
	 * @see com.mutation.report.html.IHTMLReport#generateReport(com.mutation.report.om.MutationRun, com.mutation.report.source.om.Project, java.io.File, com.mutation.report.html.VelocityRenderer)
	 */
	public void generateReport(MutationRun report, Project project, File outputDirectory, VelocityRenderer renderer)
			throws IOException, RenderException {
		VelocityContext vcontext = new VelocityContext();
		vcontext.put("report", report);
		vcontext.put("project", project);

		StringWriter w = new StringWriter();

		renderer.render(templateName,  vcontext, w);

		FileWriter writer = new FileWriter(new File(outputDirectory, "index.html"));
		writer.write(w.toString());
		writer.close();
	}

	public String getTtitle() {
		return "Index";
	}
}
