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
import java.util.List;
import java.util.Map;

import com.retroduction.carma.report.generator.IRenderer;
import com.retroduction.carma.report.generator.RendererException;
import com.retroduction.carma.report.generator.reportobjects.ClassCoverageInfoCreator;
import com.retroduction.carma.report.generator.reportobjects.CoverageInfo;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * creates list of classes report
 * 
 * @author mike
 * 
 */
public class ClassListReport implements ICoverageReport {
	public static final String HTMLFILE = "classlist.html";

	ClassCoverageInfoCreator classInfoCreator;

	private String templateName = HTMLFILE;

	public void generateReport(MutationRun report, Project project, IRenderer renderer) throws RendererException {
		Map<String, Object> ctx = new HashMap<String, Object>();

		List<ClassUnderTest> cut = report.getClassUnderTest();
		
		List<CoverageInfo> classes = this.classInfoCreator.createCoverageInfo(cut);

		ctx.put("classes", classes);
		renderer.render(this.templateName, ctx, HTMLFILE);
	}

	public void setClassInfoCreator(ClassCoverageInfoCreator classInfoCreator) {
		this.classInfoCreator = classInfoCreator;
	}
}
