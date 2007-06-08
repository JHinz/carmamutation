package com.retroduction.carma.application.resolver;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import com.retroduction.carma.application.resolver.BruteForceResolver;
import com.retroduction.carma.application.resolver.util.ExcludeFilter;
import com.retroduction.carma.application.resolver.util.FilterConfiguration;
import com.retroduction.carma.core.runner.ClassDescription;

import junit.framework.TestCase;

public class BruteForceResolverTestCase extends TestCase {

	public void testGetClassesWithoutExcludeFilterButWithIncludeFilterSet() throws MalformedURLException {

		File testClassPath = new File("src/test/it/it0001/testclasses/");

		FilterConfiguration filters = new FilterConfiguration();

		BruteForceResolver resolver = new BruteForceResolver();
		resolver.setFilterConfiguration(filters);
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

	public void testGetClassesWithExcludeFilterSet() throws MalformedURLException {

		File testClassPath = new File("src/test/it/it0001/testclasses/");

		ExcludeFilter testExcludeFilter = new ExcludeFilter();
		testExcludeFilter.setExcludePattern("sub1");

		FilterConfiguration filters = new FilterConfiguration();
		filters.setTestClassExcludeFilter(testExcludeFilter);
		
		BruteForceResolver resolver = new BruteForceResolver();
		resolver.setFilterConfiguration(filters);
		resolver.setClassesPath(testClassPath);
		resolver.setTestClassesPath(testClassPath);

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
