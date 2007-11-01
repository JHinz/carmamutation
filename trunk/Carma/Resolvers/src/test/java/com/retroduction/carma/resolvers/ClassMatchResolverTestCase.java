package com.retroduction.carma.resolvers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class ClassMatchResolverTestCase extends TestCase {

	public void test_CustomTestCaseSuffix() {

		ClassMatchResolver resolver = new ClassMatchResolver();

		Set<String> input = new HashSet<String>();

		input.add("com.retroduction.carma.test.Class1");
		input.add("com.retroduction.carma.test.Class2");

		resolver.setTestNameSuffix("PussyCat");

		HashMap<String, Set<String>> result = resolver.resolve(input);

		assertEquals(2, result.size());

		assertTrue(result.get("com.retroduction.carma.test.Class1").contains(
				"com.retroduction.carma.test.Class1PussyCat"));

		assertTrue(result.get("com.retroduction.carma.test.Class2").contains(
				"com.retroduction.carma.test.Class2PussyCat"));

	}

	public void test_DefaultTestCaseSuffix() {

		ClassMatchResolver resolver = new ClassMatchResolver();

		Set<String> input = new HashSet<String>();

		input.add("com.retroduction.carma.test.Class1");
		input.add("com.retroduction.carma.test.Class2");

		HashMap<String, Set<String>> result = resolver.resolve(input);

		assertEquals(2, result.size());

		assertTrue(result.get("com.retroduction.carma.test.Class1").contains("com.retroduction.carma.test.Class1Test"));

		assertTrue(result.get("com.retroduction.carma.test.Class2").contains("com.retroduction.carma.test.Class2Test"));
	}
	public void test_CustomTestCasePrefix() {

		ClassMatchResolver resolver = new ClassMatchResolver();

		Set<String> input = new HashSet<String>();

		input.add("com.retroduction.carma.test.Class1");
		input.add("Class2");

		resolver.setTestNameSuffix("");
		resolver.setTestNamePrefix("PussyCat");

		HashMap<String, Set<String>> result = resolver.resolve(input);

		assertEquals(2, result.size());

		assertTrue(result.get("com.retroduction.carma.test.Class1").contains(
				"com.retroduction.carma.test.PussyCatClass1"));

		assertTrue(result.get("Class2").contains(
				"PussyCatClass2"));

	}
}
