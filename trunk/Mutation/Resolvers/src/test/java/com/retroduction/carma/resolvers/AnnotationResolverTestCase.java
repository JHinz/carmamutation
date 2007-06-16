package com.retroduction.carma.resolvers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.ClassDescription;

public class AnnotationResolverTestCase extends TestCase {

	public void testGetClasses() throws MalformedURLException {

		File testClassPath = new File("src/test/it/it0002/target/test-classes/");

		AnnotationResolver resolver = new AnnotationResolver();
		resolver.setClassesPath(new File[] { testClassPath });
		resolver.setTestClassesPath(new File[] { testClassPath });

		SortedSet<ClassDescription> sortedResults = new TreeSet<ClassDescription>(resolver.resolve());

		assertEquals(2, sortedResults.size());

		Iterator<ClassDescription> resultIterator = sortedResults.iterator();

		ClassDescription resultingClassDescription;

		resultingClassDescription = resultIterator.next();

		assertEquals("different.sample.Class", resultingClassDescription.getQualifiedClassName());

		assertEquals(1, resultingClassDescription.getAssociatedTestNames().size());

		assertTrue(resultingClassDescription.getAssociatedTestNames()
				.contains("sub2.AnotherSampleClassUsingAnnotation"));

		resultingClassDescription = resultIterator.next();

		assertEquals("sample.Sample", resultingClassDescription.getQualifiedClassName());
		assertEquals(2, resultingClassDescription.getAssociatedTestNames().size());

		assertTrue(resultingClassDescription.getAssociatedTestNames().contains("sub1.SampleClassUsingAnnotation"));
		assertTrue(resultingClassDescription.getAssociatedTestNames()
				.contains("sub2.AnotherSampleClassUsingAnnotation"));

	}

}
