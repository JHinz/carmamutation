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

		result = verifier.removeExcludedClasses(potentialClasses);

		assertEquals(0, result.size());

		potentialClasses = new HashSet<String>();
		potentialClasses.add("package.Class");
		potentialClasses.add("");

		result = verifier.removeExcludedClasses(potentialClasses);

		assertEquals(2, result.size());
		assertTrue(result.contains("package.Class"));
		assertTrue(result.contains(""));

		potentialClasses = new HashSet<String>();
		potentialClasses.add(null);

		try {
			result = verifier.removeExcludedClasses(potentialClasses);
			assertTrue(false);
		} catch (NullPointerException e) {
		}

	}

	public void test_OnlyIncludeFilterUsed() {

		FilterVerifier verifier = new FilterVerifier();

		FilterConfiguration filterConfig = new FilterConfiguration();
		filterConfig.setIncludeFilter(new IncludeFilter(".*Special.*"));

		verifier.setFilterConfiguration(filterConfig);

		Set<String> potentialClasses = null;
		Set<String> result = null;

		potentialClasses = new HashSet<String>();

		result = verifier.removeExcludedClasses(potentialClasses);

		assertEquals(0, result.size());

		potentialClasses = new HashSet<String>();
		potentialClasses.add("package.Class");
		potentialClasses.add("");
		potentialClasses.add("package.SomeSpecialClass");
		potentialClasses.add("SomeReallySpecialClass");
		potentialClasses.add("AnotherspecialClass");

		result = verifier.removeExcludedClasses(potentialClasses);

		assertEquals(2, result.size());
		assertTrue(result.contains("package.SomeSpecialClass"));
		assertTrue(result.contains("SomeReallySpecialClass"));

	}

	public void test_OnlyExcludeFilterUsed() {

		FilterVerifier verifier = new FilterVerifier();

		FilterConfiguration filterConfig = new FilterConfiguration();
		filterConfig.setExcludeFilter(new ExcludeFilter(".*Special.*"));

		verifier.setFilterConfiguration(filterConfig);

		Set<String> potentialClasses = null;
		Set<String> result = null;

		potentialClasses = new HashSet<String>();

		result = verifier.removeExcludedClasses(potentialClasses);

		assertEquals(0, result.size());

		potentialClasses = new HashSet<String>();
		potentialClasses.add("package.Class");
		potentialClasses.add("");
		potentialClasses.add("package.SomeSpecialClass");
		potentialClasses.add("SomeReallySpecialClass");
		potentialClasses.add("AnotherspecialClass");

		result = verifier.removeExcludedClasses(potentialClasses);

		assertEquals(3, result.size());
		assertTrue(result.contains("package.Class"));
		assertTrue(result.contains(""));
		assertTrue(result.contains("AnotherspecialClass"));

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

		result = verifier.removeExcludedClasses(potentialClasses);

		assertEquals(0, result.size());

		potentialClasses = new HashSet<String>();
		potentialClasses.add("packageA.SomeSpecialClass");
		potentialClasses.add("packageB.CustomSpecialClass");
		potentialClasses.add("packageC.CustomClass");
		potentialClasses.add("packageD.Class");

		result = verifier.removeExcludedClasses(potentialClasses);

		assertEquals(1, result.size());
		assertTrue(result.contains("packageC.CustomClass"));

	}
}
