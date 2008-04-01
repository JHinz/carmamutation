/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.resolvers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class AnnotationResolverTestCase extends TestCase {

	public void testGetClasses() throws MalformedURLException {

		File testClassPath = new File("src/test/it/it0002/target/test-classes/");

		AnnotationResolver resolver = new AnnotationResolver();
		resolver.setTestClassesPath(new File[] { testClassPath });

		Set<String> classes = new HashSet<String>();

		classes.add("sub1.SampleClassUsingAnnotation");

		HashMap<String, Set<String>> result = resolver.resolve(classes);

		assertEquals(1, result.size());
		assertEquals(1, result.get("sub1.SampleClassUsingAnnotation").size());
		assertTrue(result.get("sub1.SampleClassUsingAnnotation").contains("sub2.AnotherSampleClassUsingAnnotation"));

	}

	public void testGetClasses_TestClassHasNoCarmaAnnotation() throws MalformedURLException {

		File testClassPath = new File("src/test/it/it0003/target/test-classes/");

		AnnotationResolver resolver = new AnnotationResolver();
		resolver.setTestClassesPath(new File[] { testClassPath });

		Set<String> classes = new HashSet<String>();

		classes.add("sub1.SampleClassUsingAnnotation");

		HashMap<String, Set<String>> result = resolver.resolve(classes);

		assertEquals(1, result.size());
		assertEquals(0, result.get("sub1.SampleClassUsingAnnotation").size());

	}

	public void testGetClasses_TestClassInvalidJavaByteCode() throws MalformedURLException {

		File testClassPath = new File("src/test/it/it0004/target/test-classes/");

		AnnotationResolver resolver = new AnnotationResolver();
		resolver.setTestClassesPath(new File[] { testClassPath });

		Set<String> classes = new HashSet<String>();

		classes.add("sub1.SampleClassUsingAnnotation");

		HashMap<String, Set<String>> result = resolver.resolve(classes);

		assertEquals(1, result.size());
		assertEquals(0, result.get("sub1.SampleClassUsingAnnotation").size());

	}

}
