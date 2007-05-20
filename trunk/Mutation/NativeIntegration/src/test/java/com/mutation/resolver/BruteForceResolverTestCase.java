package com.mutation.resolver;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.mutation.resolver.util.ExcludeFilter;
import com.mutation.runner.ClassDescription;

import junit.framework.TestCase;

public class BruteForceResolverTestCase extends TestCase {

	public void testGetClassesWithoutExcludeFilterButWithIncludeFilterSet() {

		File testClassPath = new File("src/test/it/it0001/testclasses/");

		BruteForceResolver resolver = new BruteForceResolver();
		resolver.setClassesPath(testClassPath);
		resolver.setTestClassesPath(testClassPath);

		List<ClassDescription> classes = resolver.resolve();

		assertEquals(2, classes.size());

		assertEquals(2, classes.get(0).getAssociatedTestNames().size());

		Iterator<String> testNameIterator;

		testNameIterator = classes.get(0).getAssociatedTestNames().iterator();

		assertEquals("TestClass1", testNameIterator.next());
		assertEquals("sub1.sub2.TestClass2", testNameIterator.next());

		assertEquals(2, classes.get(1).getAssociatedTestNames().size());

		testNameIterator = classes.get(1).getAssociatedTestNames().iterator();

		assertEquals("TestClass1", testNameIterator.next());
		assertEquals("sub1.sub2.TestClass2", testNameIterator.next());

	}

	public void testGetClassesWithExcludeFilterSet() {

		File testClassPath = new File("src/test/it/it0001/testclasses/");

		BruteForceResolver resolver = new BruteForceResolver();
		resolver.setClassesPath(testClassPath);
		resolver.setTestClassesPath(testClassPath);

		ExcludeFilter filter = new ExcludeFilter();
		filter.setExcludePattern("sub1");
		resolver.setTestClassExcludeFilter(filter);

		List<ClassDescription> classes = resolver.resolve();

		Iterator<String> testNameIterator;

		assertEquals(2, classes.size());

		assertEquals(1, classes.get(0).getAssociatedTestNames().size());

		testNameIterator = classes.get(1).getAssociatedTestNames().iterator();
		assertEquals("TestClass1", testNameIterator.next());

		assertEquals(1, classes.get(1).getAssociatedTestNames().size());

		testNameIterator = classes.get(1).getAssociatedTestNames().iterator();
		assertEquals("TestClass1", testNameIterator.next());

	}
}