package com.retroduction.carma.resolvers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.ClassDescription;

public class BruteForceResolverTestCase extends TestCase {

	public void testGetClassesWithoutExcludeFilterButWithIncludeFilterSet() throws MalformedURLException {

		File testClassPath = new File("src/test/it/it0001/testclasses/");

		BruteForceResolver resolver = new BruteForceResolver();
		resolver.setClassesPath(new File[] { testClassPath });
		resolver.setTestClassesPath(new File[] { testClassPath });

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
}
