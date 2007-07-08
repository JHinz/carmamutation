package com.retroduction.carma.core.api.eventlisteners.om;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.om.TestedClassInfo;
import com.retroduction.carma.utilities.ToStringUtils;

public class ProcessingClassUnderTest implements IEvent {

	private TestedClassInfo classUnderTest;

	public TestedClassInfo getClassUnderTest() {
		return classUnderTest;
	}

	public ProcessingClassUnderTest(TestedClassInfo classUnderTest) {
		super();
		this.classUnderTest = classUnderTest;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}

}
