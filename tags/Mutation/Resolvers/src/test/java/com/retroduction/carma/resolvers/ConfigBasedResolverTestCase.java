package com.retroduction.carma.resolvers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.ClassDescription;

public class ConfigBasedResolverTestCase extends TestCase {

	public void test_ReadConfig_MultiLineBaseTest() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.a.Class1=com.b.TestClass1,org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setClassesPath(null);
		resolver.setTestClassesPath(null);

		SortedSet<ClassDescription> result = new TreeSet<ClassDescription>(resolver.parseInputConfiguration(inputConfig
				.toString()));

		assertEquals(2, result.size());

		Iterator<ClassDescription> resultIterator = result.iterator();

		ClassDescription resultClass;

		resultClass = resultIterator.next();

		assertEquals("com.a.Class1", resultClass.getQualifiedClassName());

		assertEquals(2, resultClass.getAssociatedTestNames().size());
		assertTrue(resultClass.getAssociatedTestNames().contains("org.AnotherTestClass2"));
		assertTrue(resultClass.getAssociatedTestNames().contains("com.b.TestClass1"));

		resultClass = resultIterator.next();

		assertEquals("com.b.Class2", resultClass.getQualifiedClassName());

		assertEquals(1, resultClass.getAssociatedTestNames().size());
		assertTrue(resultClass.getAssociatedTestNames().contains("com.b.TestClass2"));

	}

	public void test_ReadConfig_EmptyClassNameTest() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("=com.b.TestClass1,org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		SortedSet<ClassDescription> result = new TreeSet<ClassDescription>(resolver.parseInputConfiguration(inputConfig
				.toString()));

		assertEquals(1, result.size());

		Iterator<ClassDescription> resultIterator = result.iterator();

		ClassDescription resultClass;

		resultClass = resultIterator.next();

		assertEquals("com.b.Class2", resultClass.getQualifiedClassName());

		assertEquals(1, resultClass.getAssociatedTestNames().size());
		assertTrue(resultClass.getAssociatedTestNames().contains("com.b.TestClass2"));

	}

	public void test_ReadConfig_MissingEqualSeperatorTest() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.b.TestClass1org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		SortedSet<ClassDescription> result = new TreeSet<ClassDescription>(resolver.parseInputConfiguration(inputConfig
				.toString()));

		assertEquals(1, result.size());

		Iterator<ClassDescription> resultIterator = result.iterator();

		ClassDescription resultClass;

		resultClass = resultIterator.next();

		assertEquals("com.b.Class2", resultClass.getQualifiedClassName());

		assertEquals(1, resultClass.getAssociatedTestNames().size());
		assertTrue(resultClass.getAssociatedTestNames().contains("com.b.TestClass2"));

	}

	public void test_ReadConfig_DuplicateEqualSeperatorTest() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.a.Class1=com.b.TestClass1=org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		SortedSet<ClassDescription> result = new TreeSet<ClassDescription>(resolver.parseInputConfiguration(inputConfig
				.toString()));

		assertEquals(1, result.size());

		Iterator<ClassDescription> resultIterator = result.iterator();

		ClassDescription resultClass;

		resultClass = resultIterator.next();

		assertEquals("com.b.Class2", resultClass.getQualifiedClassName());

		assertEquals(1, resultClass.getAssociatedTestNames().size());
		assertTrue(resultClass.getAssociatedTestNames().contains("com.b.TestClass2"));

	}

	public void test_ReadConfig_SkipComments_CompleteCommentLine() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("#com.b.TestClass1=org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		SortedSet<ClassDescription> result = new TreeSet<ClassDescription>(resolver.parseInputConfiguration(inputConfig
				.toString()));

		assertEquals(1, result.size());

		Iterator<ClassDescription> resultIterator = result.iterator();

		ClassDescription resultClass;

		resultClass = resultIterator.next();

		assertEquals("com.b.Class2", resultClass.getQualifiedClassName());

		assertEquals(1, resultClass.getAssociatedTestNames().size());
		assertTrue(resultClass.getAssociatedTestNames().contains("com.b.TestClass2"));

	}

	public void test_ReadConfig_SkipComments_PartialCommentLine() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.b.TestClass1=org.AnotherTestClass2#Comment\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		SortedSet<ClassDescription> result = new TreeSet<ClassDescription>(resolver.parseInputConfiguration(inputConfig
				.toString()));

		assertEquals(2, result.size());

		Iterator<ClassDescription> resultIterator = result.iterator();

		ClassDescription resultClass;

		resultClass = resultIterator.next();

		assertEquals("com.b.Class2", resultClass.getQualifiedClassName());
		assertEquals(1, resultClass.getAssociatedTestNames().size());
		assertTrue(resultClass.getAssociatedTestNames().contains("com.b.TestClass2"));

		resultClass = resultIterator.next();

		assertEquals("com.b.TestClass1", resultClass.getQualifiedClassName());

		assertEquals(1, resultClass.getAssociatedTestNames().size());
		assertTrue(resultClass.getAssociatedTestNames().contains("org.AnotherTestClass2"));

	}

	public void test_ReadInputStream() throws IOException {

		ByteArrayInputStream inputStream = new ByteArrayInputStream("Test123\nTest234".getBytes());

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		String result = resolver.readInputConfiguration(inputStream);

		assertEquals("Test123\nTest234\n", result);
	}
}
