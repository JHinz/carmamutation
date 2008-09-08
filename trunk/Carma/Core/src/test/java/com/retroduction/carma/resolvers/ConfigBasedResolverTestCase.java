/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.resolvers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class ConfigBasedResolverTestCase extends TestCase {

	public void test_ReadConfig_MultiLineBaseTest() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.a.Class1=com.b.TestClass1,org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setTestClassesPath(null);

		Set<String> classes = new HashSet<String>();

		classes.add("com.a.Class1");
		classes.add("com.c.Class3");

		HashMap<String, Set<String>> result = resolver.parseInputConfiguration(inputConfig.toString(), classes);

		assertEquals(2, result.size());

		assertEquals(2, result.get("com.a.Class1").size());
		assertTrue(result.get("com.a.Class1").contains("com.b.TestClass1"));
		assertTrue(result.get("com.a.Class1").contains("org.AnotherTestClass2"));

		assertEquals(0, result.get("com.c.Class3").size());
	}

	public void test_ReadConfig_EmptyClassNameTest() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("=com.b.TestClass1,org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setTestClassesPath(null);

		Set<String> classes = new HashSet<String>();

		classes.add("com.a.Class1");
		classes.add("com.b.Class2");

		HashMap<String, Set<String>> result = resolver.parseInputConfiguration(inputConfig.toString(), classes);

		assertEquals(2, result.size());

		assertEquals(0, result.get("com.a.Class1").size());
		
		assertEquals(1, result.get("com.b.Class2").size());
		assertTrue(result.get("com.b.Class2").contains("com.b.TestClass2"));

	}

	public void test_ReadConfig_MissingEqualSeperatorTest() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.a.Class1com.b.TestClass1,org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setTestClassesPath(null);

		Set<String> classes = new HashSet<String>();

		classes.add("com.a.Class1");
		classes.add("com.b.Class2");

		HashMap<String, Set<String>> result = resolver.parseInputConfiguration(inputConfig.toString(), classes);

		assertEquals(2, result.size());

		assertEquals(0, result.get("com.a.Class1").size());
		
		assertEquals(1, result.get("com.b.Class2").size());
		assertTrue(result.get("com.b.Class2").contains("com.b.TestClass2"));
	}

	public void test_ReadConfig_DuplicateEqualSeperatorTest() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.a.Class1=com.b.TestClass1=org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setTestClassesPath(null);

		Set<String> classes = new HashSet<String>();

		classes.add("com.a.Class1");
		classes.add("com.b.Class2");

		HashMap<String, Set<String>> result = resolver.parseInputConfiguration(inputConfig.toString(), classes);

		assertEquals(2, result.size());

		assertEquals(0, result.get("com.a.Class1").size());

		assertEquals(1, result.get("com.b.Class2").size());
		assertTrue(result.get("com.b.Class2").contains("com.b.TestClass2"));
	}

	public void test_ReadConfig_SkipComments_CompleteCommentLine() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("#com.b.TestClass1=org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setTestClassesPath(null);

		Set<String> classes = new HashSet<String>();

		classes.add("com.a.Class1");
		classes.add("com.b.Class2");

		HashMap<String, Set<String>> result = resolver.parseInputConfiguration(inputConfig.toString(), classes);

		assertEquals(2, result.size());

		assertEquals(0, result.get("com.a.Class1").size());

		assertEquals(1, result.get("com.b.Class2").size());
		assertTrue(result.get("com.b.Class2").contains("com.b.TestClass2"));
	}

	public void test_ReadConfig_SkipComments_PartialCommentLine() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.a.Class1=com.b.TestClass1#,org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setTestClassesPath(null);

		Set<String> classes = new HashSet<String>();

		classes.add("com.a.Class1");
		classes.add("com.b.Class2");

		HashMap<String, Set<String>> result = resolver.parseInputConfiguration(inputConfig.toString(), classes);

		assertEquals(2, result.size());

		assertEquals(1, result.get("com.a.Class1").size());
		assertTrue(result.get("com.a.Class1").contains("com.b.TestClass1"));
		
		assertEquals(1, result.get("com.b.Class2").size());
		assertTrue(result.get("com.b.Class2").contains("com.b.TestClass2"));
	}

	public void test_ReadInputStream() throws IOException {

		ByteArrayInputStream inputStream = new ByteArrayInputStream("Test123\nTest234".getBytes());

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		String result = resolver.readInputConfiguration(inputStream);

		assertEquals("Test123\nTest234\n", result);
	}
}
