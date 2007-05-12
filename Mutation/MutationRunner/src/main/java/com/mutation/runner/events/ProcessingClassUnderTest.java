package com.mutation.runner.events;

import com.mutation.runner.ClassDescription;
import com.mutation.runner.utililties.ToStringUtils;

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
