/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
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
