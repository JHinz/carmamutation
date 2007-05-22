package com.mutation.resolver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.mutation.runner.ClassDescription;

public class ConfigBasedResolverTestCase extends TestCase {

	public void test_ReadConfig_MultiLineBaseTest() {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.a.Class1=com.b.TestClass1,org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		List<ClassDescription> result = resolver.parseInputConfiguration(inputConfig.toString());

		assertEquals(2, result.size());
		assertEquals("com.a.Class1", result.get(0).getQualifiedClassName());
		assertEquals("com.b.Class2", result.get(1).getQualifiedClassName());

		List<String> testNames;

		testNames = new ArrayList<String>();
		testNames.addAll(result.get(0).getAssociatedTestNames());

		assertEquals(2, testNames.size());
		assertEquals("org.AnotherTestClass2", testNames.get(0));
		assertEquals("com.b.TestClass1", testNames.get(1));

		testNames = new ArrayList<String>();
		testNames.addAll(result.get(1).getAssociatedTestNames());

		assertEquals(1, testNames.size());
		assertEquals("com.b.TestClass2", testNames.get(0));

	}

	public void test_ReadConfig_EmptyClassNameTest() {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("=com.b.TestClass1,org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		List<ClassDescription> result = resolver.parseInputConfiguration(inputConfig.toString());

		assertEquals(1, result.size());
		assertEquals("com.b.Class2", result.get(0).getQualifiedClassName());

		List<String> testNames;

		testNames = new ArrayList<String>();
		testNames.addAll(result.get(0).getAssociatedTestNames());

		assertEquals(1, testNames.size());
		assertEquals("com.b.TestClass2", testNames.get(0));

	}

	public void test_ReadConfig_MissingEqualSeperatorTest() {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.b.TestClass1org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		List<ClassDescription> result = resolver.parseInputConfiguration(inputConfig.toString());

		assertEquals(1, result.size());
		assertEquals("com.b.Class2", result.get(0).getQualifiedClassName());

		List<String> testNames;

		testNames = new ArrayList<String>();
		testNames.addAll(result.get(0).getAssociatedTestNames());

		assertEquals(1, testNames.size());
		assertEquals("com.b.TestClass2", testNames.get(0));

	}

	public void test_ReadConfig_DuplicateEqualSeperatorTest() {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.a.Class1=com.b.TestClass1=org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		List<ClassDescription> result = resolver.parseInputConfiguration(inputConfig.toString());

		assertEquals(1, result.size());
		assertEquals("com.b.Class2", result.get(0).getQualifiedClassName());

		List<String> testNames;

		testNames = new ArrayList<String>();
		testNames.addAll(result.get(0).getAssociatedTestNames());

		assertEquals(1, testNames.size());
		assertEquals("com.b.TestClass2", testNames.get(0));

	}

	public void test_ReadInputStream() throws IOException {

		ByteArrayInputStream inputStream = new ByteArrayInputStream("Test123\nTest234".getBytes());

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		String result = resolver.readInputConfiguration(inputStream);

		assertEquals("Test123\nTest234\n", result);
	}
}
