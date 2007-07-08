package com.retroduction.carma.report.generator.html.coverage;

import java.util.HashMap;
import java.util.Map;

import com.retroduction.carma.report.generator.IRenderer;
import com.retroduction.carma.report.generator.RendererException;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * creates index page 
 * @author mike
 *
 */
public class IndexReport implements ICoverageReport {
	public static final String HTMLFILE = "index.html";
	public void generateReport(MutationRun report, Project project, IRenderer renderer)
			throws RendererException {

		
		Map<String, Object> ctx = new HashMap<String, Object>();

		renderer.render(HTMLFILE, ctx, HTMLFILE);
	}


}
