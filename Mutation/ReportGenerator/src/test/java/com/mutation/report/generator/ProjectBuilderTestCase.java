package com.mutation.report.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

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

	public void testExtractClassName_ValidName1() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("123", builder.extractClassName(new URL("file:123.java")));
	}

	public void testExtractClassName_ValidName2() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("A123B", builder.extractClassName(new URL("file:A123B.java")));
	}

	public void testExtractClassName_ValidNameWithPackage() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("A123B", builder.extractClassName(new URL("file:a/5/A123B.java")));
	}

	public void testExtractClassName_EmptyName() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("", builder.extractClassName(new URL("file:")));
	}

	public void testExtractClassName_InvalidNameWithPackage() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("A123B", builder.extractClassName(new URL("file:a/5/A123B.java")));
	}

	public void testExtractClassName_ValidNameWithPackage_MissingCorrectEnding_LongName() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("", builder.extractClassName(new URL("file:a/5/A123B")));
	}

	public void testExtractClassName_ValidNameWithPackage_MissingCorrectEnding_ShortName() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("", builder.extractClassName(new URL("file:a")));
	}

	public void testExtractPackageName_ValidNameWithPackage() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("com.a.b.c", builder.extractPackageName(new URL("file:src/main/java"), new URL(
				"file:src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage2() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("main.java.com.a.b.c", builder.extractPackageName(new URL("file:src/"), new URL(
				"file:src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage3() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("main.java.com.a.b.c", builder.extractPackageName(new URL("file:src"), new URL(
				"file:src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage4() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("src.main.java.com.a.b.c", builder.extractPackageName(new URL("file:"), new URL(
				"file:src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage_SourceFolderMismatch1() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("", builder.extractPackageName(new URL("file:/"), new URL(
				"file:src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage_SourceFolderMismatch2() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("", builder.extractPackageName(new URL("file:/a"), new URL(
				"file:/b/src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage_AbsoluteNames1() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("com.a.b.c", builder.extractPackageName(new URL("file:/src/main/java"), new URL(
				"file:/src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage_AbsoluteNames2() throws MalformedURLException {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("com.a.b.c", builder.extractPackageName(new URL("file:/src/main/java/"), new URL(
				"file:/src/main/java/com/a/b/c/Source.java")));
	}
}
