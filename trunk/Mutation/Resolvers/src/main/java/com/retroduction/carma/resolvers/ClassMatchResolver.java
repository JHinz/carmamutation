package com.retroduction.carma.resolvers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.retroduction.carma.core.api.resolvers.ITestClassResolver;

public class ClassMatchResolver implements ITestClassResolver {

	private String testNameSuffix = "Test";

	public HashMap<String, Set<String>> resolve(Set<String> classNames) {

		HashMap<String, Set<String>> result = new HashMap<String, Set<String>>();

		for (String clazz : classNames) {

			String testName = clazz + testNameSuffix;

			HashSet<String> tests = new HashSet<String>();

			tests.add(testName);

			result.put(clazz, tests);

		}

		return result;
	}

	public void setTestNameSuffix(String testNameSuffix) {
		this.testNameSuffix = testNameSuffix;
	}

}
