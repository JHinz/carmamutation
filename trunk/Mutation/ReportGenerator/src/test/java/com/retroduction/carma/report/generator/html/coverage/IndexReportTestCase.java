package com.retroduction.carma.report.generator.html.coverage;

import junit.framework.TestCase;

import com.retroduction.carma.annotations.TestClassToClassMapping;
import com.retroduction.carma.report.generator.html.MockRenderer;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.MutationRun;

@TestClassToClassMapping(classNames = { "com.retroduction.carma.report.generator.html.coverage.IndexReport" })
public class IndexReportTestCase extends TestCase {

	public void testGenerateReport(){
		IndexReport object = new IndexReport();
		Project project = null;
		MockRenderer renderer = new MockRenderer();
		MutationRun report = null;
		object.generateReport(report, project, renderer);
		assertEquals(0, renderer.context.size());
		assertEquals(IndexReport.HTMLFILE, renderer.templateName);
		assertEquals(IndexReport.HTMLFILE, renderer.outputFile);
		
		
	}
}
