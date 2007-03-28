package com.mutation.testsetresolver;

import java.util.HashSet;
import java.util.Set;

import com.mutation.ITestSetResolver;
import com.mutation.events.IEventListener;
import com.mutation.events.TestSetDetermined;

/**
 * determines test set as a single test per class using a suffix naming convention.
 * @author mike
 *
 */
public class OneTestPerClassResolver implements ITestSetResolver {
	private String testCaseSuffix;
	public Set<String> determineTests(String classUnderTestName, IEventListener eventListener) {
		String testClassName = classUnderTestName + this.testCaseSuffix;
		Set<String> tests = new HashSet<String>();
		tests.add(testClassName);
		eventListener.notifyEvent(new TestSetDetermined(classUnderTestName, tests));
		return tests;
	}
	public void setTestCaseSuffix(String testCaseSuffix) {
		this.testCaseSuffix = testCaseSuffix;
	}

}
