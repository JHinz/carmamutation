package com.mutation.resolver;

import java.io.File;
import java.util.List;

import com.mutation.BruteForceResolver;
import com.mutation.runner.ClassDescription;

import junit.framework.TestCase;

public class ClassAndTestClassResolverTestCase extends TestCase {

	public void testGetClassesWithoutExcludeFilterSet() {

		File classPath = new File("src/test/it/it0001/classes/");
		File testClassPath = new File("src/test/it/it0001/testclasses/");

		BruteForceResolver resolver = new BruteForceResolver();
		resolver.setClassesPath(classPath);
		resolver.setTestClassesPath(testClassPath);

		List<ClassDescription> classes = resolver.resolve();

		assertEquals(2, classes.size());

		assertEquals(2, classes.get(0).getAssociatedTestNames().size());

		assertEquals("sub1.sub2.TestClass2", classes.get(0).getAssociatedTestNames().get(0));
		assertEquals("TestClass1", classes.get(0).getAssociatedTestNames().get(1));

		assertEquals(2, classes.get(1).getAssociatedTestNames().size());

		assertEquals("sub1.sub2.TestClass2", classes.get(1).getAssociatedTestNames().get(0));
		assertEquals("TestClass1", classes.get(1).getAssociatedTestNames().get(1));

	}
	
	public void testGetClassesWithExcludeFilterSet() {

		File classPath = new File("src/test/it/it0001/classes/");
		File testClassPath = new File("src/test/it/it0001/testclasses/");

		BruteForceResolver resolver = new BruteForceResolver();
		resolver.setClassesPath(classPath);
		resolver.setTestClassesPath(testClassPath);
		resolver.setExcludePattern("sub1");

		List<ClassDescription> classes = resolver.resolve();

		assertEquals(2, classes.size());

		assertEquals(1, classes.get(0).getAssociatedTestNames().size());

		assertEquals("TestClass1", classes.get(0).getAssociatedTestNames().get(0));

		assertEquals(1, classes.get(1).getAssociatedTestNames().size());

		assertEquals("TestClass1", classes.get(1).getAssociatedTestNames().get(0));

	}
}
