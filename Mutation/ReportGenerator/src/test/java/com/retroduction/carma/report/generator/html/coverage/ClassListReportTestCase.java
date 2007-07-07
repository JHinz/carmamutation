package com.retroduction.carma.report.generator.html.coverage;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.retroduction.carma.annotations.TestClassToClassMapping;
import com.retroduction.carma.report.generator.html.MockRenderer;
import com.retroduction.carma.report.generator.mock.ClassCoverageInfoCreatorMock;
import com.retroduction.carma.report.generator.reportobjects.CoverageInfo;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRun;

@TestClassToClassMapping(classNames = { "com.retroduction.carma.report.generator.html.coverage.ClassListReport" })
public class ClassListReportTestCase extends TestCase {

	public void testGenerateReport() {
		ClassCoverageInfoCreatorMock classInfoCreator = new ClassCoverageInfoCreatorMock();
		classInfoCreator.result = new ArrayList<CoverageInfo>();

		ClassListReport ctt = new ClassListReport();
		ctt.setClassInfoCreator(classInfoCreator);

		MutationRun report = new MutationRun();
		ClassUnderTest cut = new ClassUnderTest();
		report.getClassUnderTest().add(cut);
		Project project = null;

		MockRenderer renderer = new MockRenderer();

		ctt.generateReport(report, project, renderer);
		assertEquals(report.getClassUnderTest(), classInfoCreator.cutList);
		assertEquals(1, renderer.context.size());
		assertEquals(classInfoCreator.result, renderer.context.get("classes"));
		assertEquals(ClassListReport.HTMLFILE, renderer.templateName);
		assertEquals(ClassListReport.HTMLFILE, renderer.outputFile);
	}

}
