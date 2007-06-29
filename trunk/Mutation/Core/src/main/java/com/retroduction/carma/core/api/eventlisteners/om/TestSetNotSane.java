package com.retroduction.carma.core.api.eventlisteners.om;

import java.util.Set;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.utilities.ToStringUtils;

public class TestSetNotSane implements IEvent {

	private Set<String> testCaseName;

	public TestSetNotSane(Set<String> testCaseName) {
		super();
		this.testCaseName = testCaseName;
	}

	public Set<String> getTestCaseName() {
		return testCaseName;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
