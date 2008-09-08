/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.resolvers;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.retroduction.carma.resolvers.util.ExcludeFilter;
import com.retroduction.carma.resolvers.util.FilterConfiguration;
import com.retroduction.carma.resolvers.util.FilterVerifier;
import com.retroduction.carma.resolvers.util.IncludeFilter;

public class FilterVerifierTestCase extends TestCase {

	public void test_FilterWithDefaultFilter() {

		FilterVerifier verifier = new FilterVerifier();

		FilterConfiguration filterConfig = new FilterConfiguration();

		verifier.setFilterConfiguration(filterConfig);

		Set<String> potentialClasses = null;
		Set<String> result = null;

		potentialClasses = new HashSet<String>();

		result = verifier.determineExcludedClassNames(potentialClasses);

		assertEquals(0, result.size());

		potentialClasses = new HashSet<String>();
		potentialClasses.add("package.Class");
		potentialClasses.add("package.Class$InnerClass");
		potentialClasses.add("");

		result = verifier.determineExcludedClassNames(potentialClasses);

		assertEquals(2, result.size());
		assertTrue(result.contains("package.Class$InnerClass"));
		assertTrue(result.contains(""));

		potentialClasses = new HashSet<String>();
		potentialClasses.add(null);

		result = verifier.determineExcludedClassNames(potentialClasses);
		assertTrue(result.contains(null));
	}

	public void test_OnlyIncludeFilterUsed() {

		FilterVerifier verifier = new FilterVerifier();

		FilterConfiguration filterConfig = new FilterConfiguration();
		filterConfig.setIncludeFilter(new IncludeFilter(".*Special.*"));

		verifier.setFilterConfiguration(filterConfig);

		Set<String> potentialClasses = null;
		Set<String> result = null;

		potentialClasses = new HashSet<String>();

		result = verifier.determineExcludedClassNames(potentialClasses);

		assertEquals(0, result.size());

		potentialClasses = new HashSet<String>();
		potentialClasses.add("package.Class");
		potentialClasses.add("");
		potentialClasses.add("package.SomeSpecialClass");
		potentialClasses.add("SomeReallySpecialClass");
		potentialClasses.add("AnotherspecialClass");

		result = verifier.determineExcludedClassNames(potentialClasses);

		assertEquals(3, result.size());
		assertTrue(result.contains("package.Class"));
		assertTrue(result.contains(""));
		assertTrue(result.contains("AnotherspecialClass"));
	}

	public void test_OnlyExcludeFilterUsed() {

		FilterVerifier verifier = new FilterVerifier();

		FilterConfiguration filterConfig = new FilterConfiguration();
		filterConfig.setExcludeFilter(new ExcludeFilter(".*Special.*"));

		verifier.setFilterConfiguration(filterConfig);

		Set<String> potentialClasses = null;
		Set<String> result = null;

		potentialClasses = new HashSet<String>();

		result = verifier.determineExcludedClassNames(potentialClasses);

		assertEquals(0, result.size());

		potentialClasses = new HashSet<String>();
		potentialClasses.add("package.Class");
		potentialClasses.add("");
		potentialClasses.add("package.SomeSpecialClass");
		potentialClasses.add("SomeReallySpecialClass");
		potentialClasses.add("AnotherspecialClass");

		result = verifier.determineExcludedClassNames(potentialClasses);

		assertEquals(3, result.size());
		assertTrue(result.contains("package.SomeSpecialClass"));
		assertTrue(result.contains("SomeReallySpecialClass"));
		assertTrue(result.contains(""));

	}

	public void test_IncludeAndExcludeFiltersUsed() {

		FilterVerifier verifier = new FilterVerifier();

		FilterConfiguration filterConfig = new FilterConfiguration();
		filterConfig.setExcludeFilter(new ExcludeFilter(".*Special.*"));
		filterConfig.setIncludeFilter(new IncludeFilter(".*Custom.*"));

		verifier.setFilterConfiguration(filterConfig);

		Set<String> potentialClasses = null;
		Set<String> result = null;

		potentialClasses = new HashSet<String>();

		result = verifier.determineExcludedClassNames(potentialClasses);

		assertEquals(0, result.size());

		potentialClasses = new HashSet<String>();
		potentialClasses.add("packageA.SomeSpecialClass");
		potentialClasses.add("packageB.CustomSpecialClass");
		potentialClasses.add("packageC.CustomClass");
		potentialClasses.add("packageD.Class");

		result = verifier.determineExcludedClassNames(potentialClasses);

		assertEquals(3, result.size());
		assertTrue(result.contains("packageA.SomeSpecialClass"));
		assertTrue(result.contains("packageB.CustomSpecialClass"));
		assertTrue(result.contains("packageD.Class"));

	}
}
