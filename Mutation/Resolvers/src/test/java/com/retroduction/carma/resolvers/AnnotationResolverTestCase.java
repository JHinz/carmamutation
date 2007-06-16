package com.retroduction.carma.resolvers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.ClassDescription;
import com.retroduction.carma.resolvers.AnnotationResolver;

public class AnnotationResolverTestCase extends TestCase {

	public void testGetClasses() throws MalformedURLException {

		File testClassPath = new File("src/test/it/it0002/test-classes/");

		AnnotationResolver resolver = new AnnotationResolver();
		resolver.setClassesPath(new File[] { testClassPath });
		resolver.setTestClassesPath(new File[] { testClassPath });

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

}
