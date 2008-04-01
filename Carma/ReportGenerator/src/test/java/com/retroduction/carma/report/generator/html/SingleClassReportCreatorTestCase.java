/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.html;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import com.retroduction.carma.annotations.TestClassToClassMapping;
import com.retroduction.carma.report.generator.SourceLineInfo;
import com.retroduction.carma.report.generator.html.coverage.SingleClassReportCreator;
import com.retroduction.carma.report.generator.mock.ClassCoverageInfoCreatorMock;
import com.retroduction.carma.report.generator.reportobjects.CoverageInfo;
import com.retroduction.carma.report.om.SourceFile;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;

@TestClassToClassMapping(classNames = { "com.retroduction.carma.report.generator.html.SingleClassReportCreator" })
public class SingleClassReportCreatorTestCase extends TestCase {
	@SuppressWarnings("unchecked")
	public void testCreateClassReport() throws IOException {
		SingleClassReportCreator creator = new SingleClassReportCreator();

		ClassCoverageInfoCreatorMock coverageInfoCreatorMock = new ClassCoverageInfoCreatorMock();
		coverageInfoCreatorMock.singleResult = new CoverageInfo("a", "b", 1, 2, 3);
		creator.setCoverageInfoCreator(coverageInfoCreatorMock);
		ClassUnderTest clazz = new ClassUnderTest();
		SourceFile sourceFile = new SourceFile();
		String reportFile = "sample.tmp";
		String templateName = "template";
		MockRenderer renderer = new MockRenderer();
		creator.createClassReport(clazz, sourceFile, reportFile, templateName, renderer);

		assertEquals(clazz, coverageInfoCreatorMock.cut);

		assertEquals(reportFile, renderer.outputFile);
		assertEquals(templateName, renderer.templateName);

		assertEquals(4, renderer.context.size());
		assertEquals(coverageInfoCreatorMock.singleResult, renderer.context.get(SingleClassReportCreator.COVERAGEINFO));
		assertEquals(clazz, renderer.context.get(SingleClassReportCreator.CLASS));
		assertNotNull(renderer.context.get(SingleClassReportCreator.SOURCE_INFO));
		assertEquals(0, ((List<SourceLineInfo>) renderer.context.get(SingleClassReportCreator.SOURCE_INFO)).size());

	}

}
