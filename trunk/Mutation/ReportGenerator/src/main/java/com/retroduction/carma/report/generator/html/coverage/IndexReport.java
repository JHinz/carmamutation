package com.retroduction.carma.report.generator.html.coverage;

import java.io.File;
import java.io.IOException;

import org.apache.velocity.VelocityContext;

import com.retroduction.carma.report.generator.html.IRenderer;
import com.retroduction.carma.report.generator.html.RenderException;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.MutationRun;


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