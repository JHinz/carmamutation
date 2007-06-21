package com.retroduction.carma.resolvers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.ClassDescription;

public class BruteForceResolverTestCase extends TestCase {

	public void testGetClassesWithoutExcludeFilterButWithIncludeFilterSet() throws MalformedURLException {

		File testClassPath = new File("src/test/it/it0001/testclasses/");

		BruteForceResolver resolver = new BruteForceResolver();
		resolver.setClassesPath(new File[] { testClassPath });
		resolver.setTestClassesPath(new File[] { testClassPath });

		SortedSet<ClassDescription> classes = new TreeSet<ClassDescription>(resolver.resolve());

		assertEquals(2, classes.size());

		Iterator<ClassDescription> resultIterator = classes.iterator();

		ClassDescription resultClass;

		resultClass = resultIterator.next();

		assertEquals(2, resultClass.getAssociatedTestNames().size());

		assertTrue(resultClass.getAssociatedTestNames().contains("TestClass1"));
		assertTrue(resultClass.getAssociatedTestNames().contains("sub1.sub2.TestClass2"));

		resultClass = resultIterator.next();

		assertEquals(2, resultClass.getAssociatedTestNames().size());

		assertTrue(resultClass.getAssociatedTestNames().contains("TestClass1"));
		assertTrue(resultClass.getAssociatedTestNames().contains("sub1.sub2.TestClass2"));

	}
}
