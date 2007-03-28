package com.mutation.events;

import mut.util.ToStringUtils;

public class ProcessingClassUnderTest implements IEvent {
	private String classUnderTest;

	public String getClassUnderTest() {
		return classUnderTest;
	}

	public ProcessingClassUnderTest(String classUnderTest) {
		super();
		this.classUnderTest = classUnderTest;
	}
	
	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
