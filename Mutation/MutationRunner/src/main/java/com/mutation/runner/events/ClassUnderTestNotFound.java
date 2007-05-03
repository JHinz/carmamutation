package com.mutation.runner.events;

import com.mutation.runner.utililties.ToStringUtils;

public class ClassUnderTestNotFound implements IErrorEvent {

	private String classNotFound;

	public ClassUnderTestNotFound(String classNotFound) {
		super();
		this.classNotFound = classNotFound;
	}

	public String getClassNotFound() {
		return classNotFound;
	}
	
	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
