package com.mutation.events;

import com.mutation.IClassSetResolver.ClassDescription;
import com.mutation.util.ToStringUtils;

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
