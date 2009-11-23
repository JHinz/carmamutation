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

public class BruteForceResolverTestCase extends TestCase {

	public void testGetClassesWithoutExcludeFilterButWithIncludeFilterSet()
			throws MalformedURLException {

		File testClassPath = new File(
				"src/test/it/org/retroduction/carma/resolvers/it0001/testclasses/");

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
		assertTrue(result.get("package1.class1").contains(
				"sub1.sub2.TestClass2"));
		assertTrue(result.get("package2.class2").contains(
				"sub1.sub2.TestClass2"));

	}
}
