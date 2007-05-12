package com.mutation.runner.events;

import java.util.List;

import com.mutation.runner.utililties.ToStringUtils;

public class TestSetDetermined implements IEvent {

	private String classUnderTestName;

	private List<String> determinedTests;

	public TestSetDetermined(String classUnderTestName, List<String> determinedTests) {
		super();
		this.classUnderTestName = classUnderTestName;
		this.determinedTests = determinedTests;
	}

	public String getClassUnderTestName() {
		return classUnderTestName;
	}

	public List<String> getDeterminedTests() {
		return determinedTests;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
