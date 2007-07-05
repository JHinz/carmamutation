package com.retroduction.carma.core.api.eventlisteners.om;

import java.util.Set;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.utilities.ToStringUtils;


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
