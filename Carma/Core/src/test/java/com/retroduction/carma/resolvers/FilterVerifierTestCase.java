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
