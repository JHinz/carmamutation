package com.mutation.resolver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import junit.framework.TestCase;

import com.mutation.BruteForceResolver;
import com.mutation.runner.ClassDescription;

public class ClassAndTestClassResolverTestCase extends TestCase {

	public void testGetClassesWithoutExcludeFilterSet() {

		File classPath = new File("src/test/it/it0001/classes/");
		File testClassPath = new File("src/test/it/it0001/testclasses/");

		BruteForceResolver resolver = new BruteForceResolver();
		resolver.setClassesPath(classPath);
		resolver.setTestClassesPath(testClassPath);

		List<ClassDescription> classes = resolver.resolve();

		assertEquals(2, classes.size());

		
		{
			List<String> testNames = new ArrayList<String>(new TreeSet<String> (classes.get(0).getAssociatedTestNames()));
			assertEquals(2, testNames.size());
	
			assertEquals("TestClass1", testNames.get(0));
			assertEquals("sub1.sub2.TestClass2",testNames.get(1));
		}
		
		{
			List<String> testNames = new ArrayList<String>(new TreeSet<String> (classes.get(1).getAssociatedTestNames()));
			assertEquals(2, testNames.size());
	
			assertEquals("TestClass1", testNames.get(0));
			assertEquals("sub1.sub2.TestClass2",testNames.get(1));
		}

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

		assertEquals("TestClass1", classes.get(0).getAssociatedTestNames().iterator().next());

		assertEquals(1, classes.get(1).getAssociatedTestNames().size());

		assertEquals("TestClass1", classes.get(1).getAssociatedTestNames().iterator().next());

	}
}
