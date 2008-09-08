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

public class BruteForceResolverTestCase extends TestCase {

	public void testGetClassesWithoutExcludeFilterButWithIncludeFilterSet() throws MalformedURLException {

		File testClassPath = new File("src/test/it/it0001/testclasses/");

		BruteForceResolver resolver = new BruteForceResolver();
		resolver.setTestClassesPath(new File[] { testClassPath });

		Set<String> classes = new HashSet<String>();

		classes.add("package1.class1");
		classes.add("package2.class2");

		HashMap<String, Set<String>> result = resolver.resolve(classes);

		assertEquals(2, result.size());

		assertTrue(result.containsKey("package1.class1"));
		assertTrue(result.containsKey("package2.class2"));

		assertEquals(2, result.get("package1.class1").size());
		assertEquals(2, result.get("package2.class2").size());

		assertTrue(result.get("package1.class1").contains("TestClass1"));
		assertTrue(result.get("package2.class2").contains("TestClass1"));
		assertTrue(result.get("package1.class1").contains("sub1.sub2.TestClass2"));
		assertTrue(result.get("package2.class2").contains("sub1.sub2.TestClass2"));

	}
}
