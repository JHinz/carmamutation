package com.mutation.report.generator.html.coverage;

import java.io.File;
import java.io.IOException;

import org.apache.velocity.VelocityContext;

import com.mutation.report.generator.html.IRenderer;
import com.mutation.report.generator.html.RenderException;
import com.mutation.report.om.MutationRun;
import com.mutation.report.source.om.Project;


public class IndexReport implements ICoverageReport {
	public static final String HTMLFILE = "index.html";
	private String templateName = TEMPLATEPATH +HTMLFILE;

	
	/* (non-Javadoc)
	 * @see com.mutation.report.html.IHTMLReport#generateReport(com.mutation.report.om.MutationRun, com.mutation.report.source.om.Project, java.io.File, com.mutation.report.html.VelocityRenderer)
	 */
	
	public void generateReport(MutationRun report, Project project, File outputDirectory, IRenderer renderer)
			throws IOException, RenderException {
		VelocityContext vcontext = new VelocityContext();
		vcontext.put("project", project);
		vcontext.put("report", report);

		File outputFile =  new File(outputDirectory, HTMLFILE);
		renderer.render(templateName,  vcontext, outputFile);
	}

	public String getTitle() {
		return "CoverageIndex";
	}
}
