package com.retroduction.carma.report.generator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import junit.framework.TestCase;

import com.retroduction.carma.annotations.TestClassToClassMapping;

@TestClassToClassMapping(classNames = { "com.retroduction.carma.report.generator.util.ProjectBuilder" })

public class ProjectBuilderTestCase extends TestCase {

	public void testExtractFileContent_EmptyStream() throws IOException {

		ProjectBuilder builder = new ProjectBuilder();

		BufferedReader reader = new BufferedReader(new StringReader(""));

		List<String> result = builder.extractFileContent(reader);

		assertNotNull(result);
		assertEquals(0, result.size());
	}

	public void testExtractFileContent_FilledStream() throws IOException {

		ProjectBuilder builder = new ProjectBuilder();

		StringBuffer buffer = new StringBuffer();
		buffer.append("abc\n");
		buffer.append("123\n");

		BufferedReader reader = new BufferedReader(new StringReader(buffer.toString()));

		List<String> result = builder.extractFileContent(reader);

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("abc\n", result.get(0));
		assertEquals("123\n", result.get(1));

	}

}
