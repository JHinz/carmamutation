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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.resolvers.ITestClassResolver;
import com.retroduction.carma.core.api.testrunners.ITestCaseInstantiationVerifier;
import com.retroduction.carma.core.om.PersistentClassInfo;
import com.retroduction.carma.core.om.TestedClassInfo;
import com.retroduction.carma.resolvers.util.ExcludeFilter;
import com.retroduction.carma.resolvers.util.FilterConfiguration;
import com.retroduction.carma.resolvers.util.FilterVerifier;

public class ResolverTestCase extends TestCase {

	private class MockTestClassResolver implements ITestClassResolver {

		private HashMap<String, Set<String>> mapping;

		public void setMapping(HashMap<String, Set<String>> mapping) {
			this.mapping = mapping;
		}

		public HashMap<String, Set<String>> resolve(Set<String> classNames) {
			return this.mapping;
		}
	}

	private class MockClassResolver implements IClassResolver {

		private Set<String> classes;

		public void setClasses(Set<String> classes) {
			this.classes = classes;
		}

		public Set<PersistentClassInfo> determineClassNames() {

			Set<PersistentClassInfo> result = new HashSet<PersistentClassInfo>();

			for (String clazz : this.classes) {
				result.add(new PersistentClassInfo(clazz));
			}

			return result;
		}

		public void setClassesBaseDir(File[] classesBaseDir) {
		}

	}

	private class MockInstantiationVerifier implements ITestCaseInstantiationVerifier {

		public HashSet<String> determineUnloadableTestClassNames(Set<String> fqClassNames) {
			return new HashSet<String>();
		}

	}

	public void test_DefaultFilter() {

		FilterConfiguration classfilterConfig = new FilterConfiguration();
		FilterConfiguration testClassfilterConfig = new FilterConfiguration();
		Resolver resolver = new Resolver();

		MockClassResolver classResolver = new MockClassResolver();
		classResolver.setClasses(new HashSet<String>(Arrays.asList("com.retroduction.carma.test.A",
				"com.retroduction.carma.test.B")));

		resolver.setClassResolver(classResolver);

		HashMap<String, Set<String>> mapping = new HashMap<String, Set<String>>();
		mapping.put("com.retroduction.carma.test.A", new HashSet<String>(Arrays.asList(
				"com.retroduction.carma.test.ATestCase", "com.retroduction.carma.test.BTestCase")));

		mapping.put("com.retroduction.carma.test.B", new HashSet<String>(Arrays.asList(
				"com.retroduction.carma.test.ATestCase", "com.retroduction.carma.test.BTestCase")));

		MockTestClassResolver testClassResolver = new MockTestClassResolver();
		testClassResolver.setMapping(mapping);

		resolver.setTestClassResolver(testClassResolver);

		FilterVerifier classFilterVerifier = new FilterVerifier();
		FilterVerifier testClassFilterVerifier = new FilterVerifier();

		classFilterVerifier.setFilterConfiguration(classfilterConfig);
		testClassFilterVerifier.setFilterConfiguration(testClassfilterConfig);

		MockInstantiationVerifier instantiationVerifier = new MockInstantiationVerifier();

		resolver.setClassFilterVerifier(classFilterVerifier);
		resolver.setTestClassFilterVerifier(testClassFilterVerifier);
		resolver.setInstantiationVerifier(instantiationVerifier);

		Set<TestedClassInfo> result = resolver.resolve();

		assertEquals(2, result.size());

		HashMap<String, TestedClassInfo> sortedClasses = new HashMap<String, TestedClassInfo>();
		for (TestedClassInfo description : result) {
			sortedClasses.put(description.getFullyQualifiedClassName(), description);
		}

		{
			TestedClassInfo testedClassInfo = sortedClasses.get("com.retroduction.carma.test.A");

			Set<PersistentClassInfo> testNames = testedClassInfo.getAssociatedTestNames();

			assertEquals(2, testNames.size());
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.ATestCase"));
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.BTestCase"));

		}

		{
			TestedClassInfo testedClassInfo = sortedClasses.get("com.retroduction.carma.test.B");

			Set<PersistentClassInfo> testNames = testedClassInfo.getAssociatedTestNames();

			assertEquals(2, testNames.size());
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.A"));
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.B"));

		}

	}

	public void test_ClassFilterUse() {

		Resolver resolver = new Resolver();

		MockClassResolver classResolver = new MockClassResolver();
		classResolver.setClasses(new HashSet<String>(Arrays.asList("com.retroduction.carma.test.A",
				"com.retroduction.carma.test.B")));

		resolver.setClassResolver(classResolver);

		HashMap<String, Set<String>> mapping = new HashMap<String, Set<String>>();
		mapping.put("com.retroduction.carma.test.A", new HashSet<String>(Arrays.asList(
				"com.retroduction.carma.test.ATestCase", "com.retroduction.carma.test.BTestCase")));

		mapping.put("com.retroduction.carma.test.B", new HashSet<String>(Arrays.asList(
				"com.retroduction.carma.test.ATestCase", "com.retroduction.carma.test.BTestCase")));

		MockTestClassResolver testClassResolver = new MockTestClassResolver();
		testClassResolver.setMapping(mapping);

		resolver.setTestClassResolver(testClassResolver);

		FilterConfiguration classfilterConfig = new FilterConfiguration();
		FilterConfiguration testClassfilterConfig = new FilterConfiguration();

		classfilterConfig.setExcludeFilter(new ExcludeFilter("(.*A)"));

		FilterVerifier classFilterVerifier = new FilterVerifier();
		FilterVerifier testClassFilterVerifier = new FilterVerifier();

		classFilterVerifier.setFilterConfiguration(classfilterConfig);
		testClassFilterVerifier.setFilterConfiguration(testClassfilterConfig);

		MockInstantiationVerifier instantiationVerifier = new MockInstantiationVerifier();

		resolver.setClassFilterVerifier(classFilterVerifier);
		resolver.setTestClassFilterVerifier(testClassFilterVerifier);
		resolver.setInstantiationVerifier(instantiationVerifier);

		Set<TestedClassInfo> result = resolver.resolve();

		assertEquals(1, result.size());

		HashMap<String, TestedClassInfo> sortedClasses = new HashMap<String, TestedClassInfo>();
		for (TestedClassInfo description : result) {
			sortedClasses.put(description.getFullyQualifiedClassName(), description);
		}

		{
			TestedClassInfo testedClassInfo = sortedClasses.get("com.retroduction.carma.test.B");

			Set<PersistentClassInfo> testNames = testedClassInfo.getAssociatedTestNames();

			assertEquals(2, testNames.size());
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.A"));
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.B"));

		}

	}

	public void test_TestClassFilterUse() {

		Resolver resolver = new Resolver();

		MockClassResolver classResolver = new MockClassResolver();
		classResolver.setClasses(new HashSet<String>(Arrays.asList("com.retroduction.carma.test.A",
				"com.retroduction.carma.test.B")));

		resolver.setClassResolver(classResolver);

		HashMap<String, Set<String>> mapping = new HashMap<String, Set<String>>();
		mapping.put("com.retroduction.carma.test.A", new HashSet<String>(Arrays.asList(
				"com.retroduction.carma.test.ATestCase", "com.retroduction.carma.test.BTestCase")));

		mapping.put("com.retroduction.carma.test.B", new HashSet<String>(Arrays.asList(
				"com.retroduction.carma.test.ATestCase", "com.retroduction.carma.test.BTestCase")));

		MockTestClassResolver testClassResolver = new MockTestClassResolver();
		testClassResolver.setMapping(mapping);

		resolver.setTestClassResolver(testClassResolver);

		FilterConfiguration classfilterConfig = new FilterConfiguration();
		FilterConfiguration testClassfilterConfig = new FilterConfiguration();

		testClassfilterConfig.setExcludeFilter(new ExcludeFilter("(.*B)"));

		FilterVerifier classFilterVerifier = new FilterVerifier();
		FilterVerifier testClassFilterVerifier = new FilterVerifier();

		classFilterVerifier.setFilterConfiguration(classfilterConfig);
		testClassFilterVerifier.setFilterConfiguration(testClassfilterConfig);

		MockInstantiationVerifier instantiationVerifier = new MockInstantiationVerifier();

		resolver.setClassFilterVerifier(classFilterVerifier);
		resolver.setTestClassFilterVerifier(testClassFilterVerifier);
		resolver.setInstantiationVerifier(instantiationVerifier);

		Set<TestedClassInfo> result = resolver.resolve();

		assertEquals(2, result.size());

		HashMap<String, TestedClassInfo> sortedClasses = new HashMap<String, TestedClassInfo>();
		for (TestedClassInfo description : result) {
			sortedClasses.put(description.getFullyQualifiedClassName(), description);
		}

		{
			TestedClassInfo testedClassInfo = sortedClasses.get("com.retroduction.carma.test.A");

			Set<PersistentClassInfo> testNames = testedClassInfo.getAssociatedTestNames();

			assertEquals(1, testNames.size());
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.ATestCase"));

		}

		{
			TestedClassInfo testedClassInfo = sortedClasses.get("com.retroduction.carma.test.B");

			Set<PersistentClassInfo> testNames = testedClassInfo.getAssociatedTestNames();

			assertEquals(1, testNames.size());
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.A"));

		}

	}
}
