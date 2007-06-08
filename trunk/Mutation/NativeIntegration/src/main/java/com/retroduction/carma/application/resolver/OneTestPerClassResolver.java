package com.retroduction.carma.application.resolver;

import java.util.HashSet;
import java.util.Set;

/**
 * determines test set as a single test per class using a suffix naming
 * convention.
 * 
 * @author mike
 * 
 */
public class OneTestPerClassResolver {
	private String testCaseSuffix;

	public Set<String> determineTests(String classUnderTestName) {
		String testClassName = classUnderTestName + this.testCaseSuffix;
		Set<String> tests = new HashSet<String>();
		tests.add(testClassName);
		return tests;
	}

	public void setTestCaseSuffix(String testCaseSuffix) {
		this.testCaseSuffix = testCaseSuffix;
	}

}
