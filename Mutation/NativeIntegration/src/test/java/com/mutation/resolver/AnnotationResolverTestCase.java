package com.mutation.resolver;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.mutation.resolver.util.ExcludeFilter;
import com.mutation.resolver.util.FilterConfiguration;
import com.mutation.runner.ClassDescription;

public class AnnotationResolverTestCase extends TestCase {

	public void testGetClasses() throws MalformedURLException {

		File testClassPath = new File("src/test/it/it0002/test-classes/");

		AnnotationResolver resolver = new AnnotationResolver();
		resolver.setFilterConfiguration(new FilterConfiguration());
		resolver.setClassesPath(testClassPath);
		resolver.setTestClassesPath(testClassPath);

		List<ClassDescription> classes = resolver.resolve();

		assertEquals(2, classes.size());

		assertEquals("different.sample.Class", classes.get(0).getQualifiedClassName());
		assertEquals(1, classes.get(0).getAssociatedTestNames().size());

		Iterator<String> testNameIterator;

		testNameIterator = classes.get(0).getAssociatedTestNames().iterator();

		assertEquals("sub2.AnotherSampleClassUsingAnnotation", testNameIterator.next());

		assertEquals("sample.Sample", classes.get(1).getQualifiedClassName());
		assertEquals(2, classes.get(1).getAssociatedTestNames().size());

		testNameIterator = classes.get(1).getAssociatedTestNames().iterator();

		assertEquals("sub2.AnotherSampleClassUsingAnnotation", testNameIterator.next());
		assertEquals("sub1.SampleClassUsingAnnotation", testNameIterator.next());
	}

	public void testGetClassesWithFilter() throws MalformedURLException {

		File testClassPath = new File("src/test/it/it0002/test-classes/");

		ExcludeFilter testExcludeFilter = new ExcludeFilter();
		testExcludeFilter.setExcludePattern("sub2");

		FilterConfiguration filters = new FilterConfiguration();
		filters.setTestClassExcludeFilter(testExcludeFilter);

		AnnotationResolver resolver = new AnnotationResolver();
		resolver.setFilterConfiguration(filters);
		resolver.setClassesPath(testClassPath);
		resolver.setTestClassesPath(testClassPath);

		List<ClassDescription> classes = resolver.resolve();

		assertEquals(1, classes.size());

		assertEquals("sample.Sample", classes.get(0).getQualifiedClassName());
		assertEquals(1, classes.get(0).getAssociatedTestNames().size());

		Iterator<String> testNameIterator = classes.get(0).getAssociatedTestNames().iterator();

		assertEquals("sub1.SampleClassUsingAnnotation", testNameIterator.next());
	}
}