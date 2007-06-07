package com.retroduction.carma.core.runner.events;

import com.retroduction.carma.core.runner.ClassDescription;
import com.retroduction.carma.core.runner.utililties.ToStringUtils;

public class ProcessingClassUnderTest implements IEvent {

	private ClassDescription classUnderTest;

	public ClassDescription getClassUnderTest() {
		return classUnderTest;
	}

	public ProcessingClassUnderTest(ClassDescription classUnderTest) {
		super();
		this.classUnderTest = classUnderTest;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}

}
