package com.mutation.events;

import com.mutation.util.ToStringUtils;

public class ProcessingClassUnderTest implements IEvent {
	private String classUnderTest;

	private String classUnderTestFile;

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

	public String getClassUnderTestFile() {
		return classUnderTestFile;
	}

	public void setClassUnderTestFile(String classUnderTestFile) {
		this.classUnderTestFile = classUnderTestFile;
	}
}
