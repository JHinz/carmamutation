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

package com.retroduction.carma.resolvers.util;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class TestCaseInstantiationVerifierTestCase extends TestCase {

	public void test_FilterInvalidURLs() {

		TestCaseInstantiationVerifier verifier = new TestCaseInstantiationVerifier();

		Set<File> inputURLs = null;
		Set<URL> validURLs = null;

		inputURLs = new HashSet<File>();
		inputURLs.add(new File("test.xml"));
		validURLs = verifier.filterInvalidURLs(inputURLs);

		assertEquals("Valid url count incorrect", 1, validURLs.size());

		inputURLs = new HashSet<File>();
		inputURLs.add(null);
		validURLs = verifier.filterInvalidURLs(inputURLs);

		assertEquals("Valid url count incorrect", 0, validURLs.size());

		inputURLs = new HashSet<File>();
		inputURLs.add(null);
		inputURLs.add(new File("/test.xml"));
		inputURLs.add(null);
		validURLs = verifier.filterInvalidURLs(inputURLs);

		assertEquals("Valid url count incorrect", 1, validURLs.size());

		if (System.getProperty("os.name").contains("Windows")) {
			assertEquals("file:/D:/test.xml", validURLs.iterator().next()
					.toString());
		} else {
			assertEquals("file:/test.xml", validURLs.iterator().next()
					.toString());
		}
	}

	public void test_InstantiationCheck() {

		TestCaseInstantiationVerifier verifier = new TestCaseInstantiationVerifier();

		Set<File> inputURLsClasses = new HashSet<File>();
		inputURLsClasses.add(new File(
				"src/test/ut/InstantiationVerifierTestCase_1/target/classes/"));
		verifier.setClassPath(inputURLsClasses);

		Set<File> inputURLsTestClasses = new HashSet<File>();
		inputURLsTestClasses
				.add(new File(
						"src/test/ut/InstantiationVerifierTestCase_1/target/test-classes/"));
		verifier.setTestClassPath(inputURLsTestClasses);

		HashSet<String> classes = new HashSet<String>();

		classes.add("InstantiationVerifierTestClass");
		classes.add("another.InstantiationVerifierTestClass");
		classes.add("InstantiationVerifierAbstractTestClass");
		classes.add("InstantiationVerifierTestInterface");
		classes.add("nonExistantClass");

		HashSet<String> result = verifier
				.determineUnloadableTestClassNames(classes);

		assertEquals(3, result.size());
		assertTrue(result.contains("InstantiationVerifierAbstractTestClass"));
		assertTrue(result.contains("InstantiationVerifierTestInterface"));
		assertTrue(result.contains("nonExistantClass"));
	}

	public void test_InvalidClassPath() {

		TestCaseInstantiationVerifier verifier = new TestCaseInstantiationVerifier();
		Set<File> inputURLs = new HashSet<File>();
		inputURLs.add(null);

		verifier.setClassPath(inputURLs);

		HashSet<String> classes = new HashSet<String>();

		classes.add("InstantiationVerifierTestClass");

		HashSet<String> result = verifier
				.determineUnloadableTestClassNames(classes);

		assertEquals(1, result.size());
		assertTrue(result.contains("InstantiationVerifierTestClass"));
	}

}
