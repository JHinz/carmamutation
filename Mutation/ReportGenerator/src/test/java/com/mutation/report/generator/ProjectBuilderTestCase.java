package com.mutation.report.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import junit.framework.TestCase;

public class ProjectBuilderTestCase extends TestCase {

	private static String FILESEP;

	static {
		FILESEP = System.getProperty("file.separator");
	}

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

	public void testExtractClassName_ValidName1() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("123", builder.extractClassName("123.java"));
	}

	public void testExtractClassName_ValidName2() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("A123B", builder.extractClassName("A123B.java"));
	}

	public void testExtractClassName_ValidNameWithPackage() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("A123B", builder.extractClassName("a" + FILESEP + "5" + FILESEP + "A123B.java"));
	}

	public void testExtractClassName_EmptyName() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("", builder.extractClassName(""));
	}

	public void testExtractClassName_InvalidNameWithPackage() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("A123B", builder.extractClassName(FILESEP + "a" + FILESEP + "5" + FILESEP + "A123B.java"));
	}

	public void testExtractClassName_ValidNameWithPackage_MissingCorrectEnding_LongName() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("", builder.extractClassName("a" + FILESEP + "5" + FILESEP + "A123B"));
	}

	public void testExtractClassName_ValidNameWithPackage_MissingCorrectEnding_ShortName() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("", builder.extractClassName("a"));
	}

	public void testExtractPackageName_ValidNameWithPackage() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("com.a.b.c", builder.extractPackageName("src/main/java", "src/main/java/com/a/b/c/Source.java"));
	}

	public void testExtractPackageName_ValidNameWithPackage2() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("main.java.com.a.b.c", builder.extractPackageName("src/", "src/main/java/com/a/b/c/Source.java"));
	}

	public void testExtractPackageName_ValidNameWithPackage3() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("main.java.com.a.b.c", builder.extractPackageName("src", "src/main/java/com/a/b/c/Source.java"));
	}
	
	public void testExtractPackageName_ValidNameWithPackage4() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("src.main.java.com.a.b.c", builder.extractPackageName("", "src/main/java/com/a/b/c/Source.java"));
	}

	public void testExtractPackageName_ValidNameWithPackage_SourceFolderMismatch1() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("", builder.extractPackageName("/", "src/main/java/com/a/b/c/Source.java"));
	}
	
	public void testExtractPackageName_ValidNameWithPackage_SourceFolderMismatch2() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("", builder.extractPackageName("/a", "/b/src/main/java/com/a/b/c/Source.java"));
	}
	
	public void testExtractPackageName_ValidNameWithPackage_AbsoluteNames1() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("com.a.b.c", builder.extractPackageName("/src/main/java", "/src/main/java/com/a/b/c/Source.java"));
	}
	
	public void testExtractPackageName_ValidNameWithPackage_AbsoluteNames2() {
		ProjectBuilder builder = new ProjectBuilder();
		assertEquals("com.a.b.c", builder.extractPackageName("/src/main/java/", "/src/main/java/com/a/b/c/Source.java"));
	}
}
