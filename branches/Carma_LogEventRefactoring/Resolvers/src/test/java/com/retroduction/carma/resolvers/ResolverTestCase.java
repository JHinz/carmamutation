package com.retroduction.carma.resolvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.api.eventlisteners.IEventListener;
import com.retroduction.carma.core.api.resolvers.IResolver;
import com.retroduction.carma.core.api.testrunners.ITestCaseInstantiationVerifier;
import com.retroduction.carma.core.api.testrunners.om.ClassDescription;
import com.retroduction.carma.resolvers.util.ExcludeFilter;
import com.retroduction.carma.resolvers.util.FilterConfiguration;
import com.retroduction.carma.resolvers.util.FilterVerifier;

public class ResolverTestCase extends TestCase {

	private class MockResolver implements IResolver {

		private Set<ClassDescription> resolvedClasses;

		public void setResolvedClasses(Set<ClassDescription> resolvedClasses) {
			this.resolvedClasses = resolvedClasses;
		}

		public Set<ClassDescription> resolve() {
			return resolvedClasses;
		}

	}

	private class MockInstantiationVerifier implements ITestCaseInstantiationVerifier {

		public HashSet<String> removeNonInstantiatableClasses(Set<String> fqClassNames) {
			return new HashSet<String>(fqClassNames);
		}

	}

	private class MockEventListener implements IEventListener {

		private ArrayList<IEvent> occuredEvents = new ArrayList<IEvent>();

		public void destroy() {
		}

		public void notifyEvent(IEvent event) {
			occuredEvents.add(event);
		}

		public ArrayList<IEvent> getOccuredEvents() {
			return occuredEvents;
		}

	}

	public void test_DefaultFilter() {

		FilterConfiguration classfilterConfig = new FilterConfiguration();
		FilterConfiguration testClassfilterConfig = new FilterConfiguration();

		Resolver resolver = prepareResolverInstance(classfilterConfig, testClassfilterConfig);

		Set<ClassDescription> result = resolver.resolve();

		assertEquals(2, result.size());

		HashMap<String, ClassDescription> sortedClasses = new HashMap<String, ClassDescription>();
		for (ClassDescription description : result)
			sortedClasses.put(description.getQualifiedClassName(), description);

		{
			ClassDescription classDescription = sortedClasses.get("com.retroduction.carma.test.A");

			Set<String> testNames = classDescription.getAssociatedTestNames();

			assertEquals(1, testNames.size());
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.A"));

		}

		{
			ClassDescription classDescription = sortedClasses.get("com.retroduction.carma.test.B");

			Set<String> testNames = classDescription.getAssociatedTestNames();

			assertEquals(2, testNames.size());
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.A"));
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.B"));

		}

	}

	public void test_FilterVerifiersAreusedIndependently_TestsFiltered() {

		FilterConfiguration classfilterConfig = new FilterConfiguration();
		FilterConfiguration testClassfilterConfig = new FilterConfiguration();

		testClassfilterConfig.setExcludeFilter(new ExcludeFilter("(.*A.*)"));

		Resolver resolver = prepareResolverInstance(classfilterConfig, testClassfilterConfig);

		Set<ClassDescription> result = resolver.resolve();

		assertEquals(2, result.size());

		HashMap<String, ClassDescription> sortedClasses = new HashMap<String, ClassDescription>();
		for (ClassDescription description : result)
			sortedClasses.put(description.getQualifiedClassName(), description);

		{
			ClassDescription classDescription = sortedClasses.get("com.retroduction.carma.test.A");

			assertEquals("com.retroduction.carma.test.A", classDescription.getQualifiedClassName());

			Set<String> testNames = classDescription.getAssociatedTestNames();

			assertEquals(0, testNames.size());

		}

		{
			ClassDescription classDescription = sortedClasses.get("com.retroduction.carma.test.B");

			Set<String> testNames = classDescription.getAssociatedTestNames();

			assertEquals(1, testNames.size());
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.B"));

		}

	}

	public void test_FilterVerifiersAreusedIndependently_ClassFiltered() {

		FilterConfiguration classfilterConfig = new FilterConfiguration();
		FilterConfiguration testClassfilterConfig = new FilterConfiguration();

		classfilterConfig.setExcludeFilter(new ExcludeFilter("(.*A.*)"));

		Resolver resolver = prepareResolverInstance(classfilterConfig, testClassfilterConfig);

		Set<ClassDescription> result = resolver.resolve();

		assertEquals(1, result.size());

		HashMap<String, ClassDescription> sortedClasses = new HashMap<String, ClassDescription>();
		for (ClassDescription description : result)
			sortedClasses.put(description.getQualifiedClassName(), description);

		{
			ClassDescription classDescription = sortedClasses.get("com.retroduction.carma.test.B");

			Set<String> testNames = classDescription.getAssociatedTestNames();

			assertEquals(2, testNames.size());
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.A"));
			assertNotNull(testNames.contains("com.retroduction.carma.testcases.B"));

		}

	}

	private Resolver prepareResolverInstance(FilterConfiguration classfilterConfig,
			FilterConfiguration testClassfilterConfig) {
		Set<ClassDescription> resolverResult1 = new HashSet<ClassDescription>();

		ClassDescription desc1 = new ClassDescription();
		desc1.setQualifiedClassName("com.retroduction.carma.test.A");

		Set<String> associatedTests = new HashSet<String>();
		associatedTests.add("com.retroduction.carma.testcases.A");
		desc1.setAssociatedTestNames(associatedTests);

		ClassDescription desc2 = new ClassDescription();
		desc2.setQualifiedClassName("com.retroduction.carma.test.B");

		Set<String> associatedTests2 = new HashSet<String>();
		associatedTests2.add("com.retroduction.carma.testcases.A");
		associatedTests2.add("com.retroduction.carma.testcases.B");
		desc2.setAssociatedTestNames(associatedTests2);

		resolverResult1.add(desc1);
		resolverResult1.add(desc2);

		Set<ClassDescription> resolverResult = resolverResult1;

		MockResolver nestedResolver = new MockResolver();
		nestedResolver.setResolvedClasses(resolverResult);

		FilterVerifier classFilterVerifier = new FilterVerifier();
		FilterVerifier testClassFilterVerifier = new FilterVerifier();

		classFilterVerifier.setFilterConfiguration(classfilterConfig);
		testClassFilterVerifier.setFilterConfiguration(testClassfilterConfig);

		MockInstantiationVerifier instantiationVerifier = new MockInstantiationVerifier();
		MockEventListener eventListener = new MockEventListener();

		Resolver resolver = new Resolver();
		resolver.setNestedResolver(nestedResolver);
		resolver.setClassFilterVerifier(classFilterVerifier);
		resolver.setTestClassFilterVerifier(testClassFilterVerifier);
		resolver.setInstantiationVerifier(instantiationVerifier);
		resolver.setEventListener(eventListener);
		return resolver;
	}

}
