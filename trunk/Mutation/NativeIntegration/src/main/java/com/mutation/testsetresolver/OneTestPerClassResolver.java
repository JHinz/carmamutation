package com.mutation.testsetresolver;

import java.util.ArrayList;
import java.util.List;

import com.mutation.runner.ITestSetResolver;

/**
 * determines test set as a single test per class using a suffix naming
 * convention.
 * 
 * @author mike
 * 
 */
public class OneTestPerClassResolver implements ITestSetResolver {
	private String testCaseSuffix;

	public List<String> determineTests(String classUnderTestName) {
		String testClassName = classUnderTestName + this.testCaseSuffix;
		List<String> tests = new ArrayList<String>();
		tests.add(testClassName);
		return tests;
	}

	public void setTestCaseSuffix(String testCaseSuffix) {
		this.testCaseSuffix = testCaseSuffix;
	}

}
