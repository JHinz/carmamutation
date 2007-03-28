package com.mutation.events;

import java.util.Set;

import mut.util.ToStringUtils;

public class TestSetDetermined implements IEvent {
	private String classUnderTestName;
	private Set<String> determinedTests;
	
	public TestSetDetermined(String classUnderTestName, Set<String> determinedTests) {
		super();
		this.classUnderTestName = classUnderTestName;
		this.determinedTests = determinedTests;
	}
	public String getClassUnderTestName() {
		return classUnderTestName;
	}
	public Set<String> getDeterminedTests() {
		return determinedTests;
	}
	
	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
