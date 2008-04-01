/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.html.coverage;

import java.util.HashMap;
import java.util.Map;

import com.retroduction.carma.report.generator.IRenderer;
import com.retroduction.carma.report.generator.html.RenderException;
import com.retroduction.carma.report.generator.reportobjects.PackageInfo;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * creates package list
 * 
 * @author mike
 * 
 */
public class PackagesReport implements ICoverageReport {
	public static final String HTMLFILE = "packages.html";

	private String templateName = HTMLFILE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mutation.report.html.IHTMLReport#generateReport(com.mutation.report.om.MutationRun,
	 *      com.mutation.report.source.om.Project, java.io.File,
	 *      com.mutation.report.html.VelocityRenderer)
	 */
	public void generateReport(MutationRun report, Project project, IRenderer renderer) throws RenderException {

		Map<String, Object> ctx = new HashMap<String, Object>();

		PackageInfo packageInfo = new PackageInfo(report.getClassUnderTest());

		ctx.put("packageInfo", packageInfo);
		ctx.put("package", "");

		renderer.render(this.templateName, ctx, HTMLFILE);

	}
}
