package com.retroduction.carma.core.api.eventlisteners.om;

import java.util.Set;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.api.testrunners.om.ClassDescription;
import com.retroduction.carma.utilities.ToStringUtils;

public class TestSetNotSane implements IEvent {

	private Set<String> testCaseName;

	private ClassDescription classDescription;

	public TestSetNotSane(Set<String> testCaseName, ClassDescription classDescription) {
		super();
		this.testCaseName = testCaseName;
		this.classDescription = classDescription;
	}

	public ClassDescription getClassDescription() {
		return classDescription;
	}

	public Set<String> getTestCaseName() {
		return testCaseName;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
