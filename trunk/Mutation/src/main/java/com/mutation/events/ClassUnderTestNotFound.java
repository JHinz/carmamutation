package com.mutation.events;

public class ClassUnderTestNotFound implements IErrorEvent {

	private String classNotFound;

	public ClassUnderTestNotFound(String classNotFound) {
		super();
		this.classNotFound = classNotFound;
	}

	public String getClassNotFound() {
		return classNotFound;
	}
}
