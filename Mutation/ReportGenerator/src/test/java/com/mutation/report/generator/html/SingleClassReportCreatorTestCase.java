package com.mutation.report.generator.html;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import com.mutation.report.generator.SourceLineInfo;
import com.mutation.report.om.ClassUnderTest;
import com.mutation.report.source.om.SourceFile;

public class SingleClassReportCreatorTestCase extends TestCase {
	@SuppressWarnings("unchecked")
	public void testCreateClassReport() throws IOException {
		SingleClassReportCreator creator = new SingleClassReportCreator();

		ClassUnderTest clazz = new ClassUnderTest();
		SourceFile sourceFile = new SourceFile();
		File reportFile = new File("sample.tmp");
		String templateName = "template";
		MockRenderer renderer = new MockRenderer();
		creator.createClassReport(clazz, sourceFile, reportFile, templateName, renderer);

		assertEquals(reportFile, renderer.outputFile);
		assertEquals(templateName, renderer.templateName);
		assertEquals(clazz, renderer.context.get(SingleClassReportCreator.CLASS));
		assertNotNull(renderer.context.get(SingleClassReportCreator.SOURCE_INFO));
		assertEquals(0, ((List<SourceLineInfo>) renderer.context.get(SingleClassReportCreator.SOURCE_INFO)).size());

	}
	
	
}
