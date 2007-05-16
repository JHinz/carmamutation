package com.mutation.report.generator.utils;

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

public class ClassNameAnalyzerTestCase extends TestCase {

	public void test_GetClassNameInfo() {

		ClassNameAnalyzer analyzer = new ClassNameAnalyzer();

		assertEquals("a1", analyzer.extractClassNameInfo("a1").getClassName());
		assertEquals("", analyzer.extractClassNameInfo("a1").getPackageName());

		assertEquals("a1", analyzer.extractClassNameInfo("123.a1").getClassName());
		assertEquals("123", analyzer.extractClassNameInfo("123.a1").getPackageName());

		assertEquals("a1", analyzer.extractClassNameInfo("123.abc.a1").getClassName());
		assertEquals("123.abc", analyzer.extractClassNameInfo("123.abc.a1").getPackageName());

		assertEquals("a1", analyzer.extractClassNameInfo("123.abc.a1").getClassName());
		assertEquals("123.abc", analyzer.extractClassNameInfo("123.abc.a1").getPackageName());

		assertEquals("", analyzer.extractClassNameInfo("").getClassName());
		assertEquals("", analyzer.extractClassNameInfo("").getPackageName());
	}

	public void testExtractClassName_ValidName1() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("123", builder.extractClassName(new URL("file:123.java")));
	}

	public void testExtractClassName_ValidName2() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("A123B", builder.extractClassName(new URL("file:A123B.java")));
	}

	public void testExtractClassName_ValidNameWithPackage() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("A123B", builder.extractClassName(new URL("file:a/5/A123B.java")));
	}

	public void testExtractClassName_EmptyName() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("", builder.extractClassName(new URL("file:")));
	}

	public void testExtractClassName_InvalidNameWithPackage() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("A123B", builder.extractClassName(new URL("file:a/5/A123B.java")));
	}

	public void testExtractClassName_ValidNameWithPackage_MissingCorrectEnding_LongName() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("", builder.extractClassName(new URL("file:a/5/A123B")));
	}

	public void testExtractClassName_ValidNameWithPackage_MissingCorrectEnding_ShortName() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("", builder.extractClassName(new URL("file:a")));
	}

	public void testExtractPackageName_ValidNameWithPackage() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("com.a.b.c", builder.extractPackageName(new URL("file:src/main/java"), new URL(
				"file:src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage2() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("main.java.com.a.b.c", builder.extractPackageName(new URL("file:src/"), new URL(
				"file:src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage3() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("main.java.com.a.b.c", builder.extractPackageName(new URL("file:src"), new URL(
				"file:src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage4() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("src.main.java.com.a.b.c", builder.extractPackageName(new URL("file:"), new URL(
				"file:src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage_SourceFolderMismatch1() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("", builder.extractPackageName(new URL("file:/"), new URL(
				"file:src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage_SourceFolderMismatch2() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("", builder.extractPackageName(new URL("file:/a"), new URL(
				"file:/b/src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage_AbsoluteNames1() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("com.a.b.c", builder.extractPackageName(new URL("file:/src/main/java"), new URL(
				"file:/src/main/java/com/a/b/c/Source.java")));
	}

	public void testExtractPackageName_ValidNameWithPackage_AbsoluteNames2() throws MalformedURLException {
		ClassNameAnalyzer builder = new ClassNameAnalyzer();
		assertEquals("com.a.b.c", builder.extractPackageName(new URL("file:/src/main/java/"), new URL(
				"file:/src/main/java/com/a/b/c/Source.java")));
	}

}
