package com.retroduction.carma.core.api.eventlisteners.om;

import com.retroduction.carma.core.api.eventlisteners.IErrorEvent;
import com.retroduction.carma.utilities.ToStringUtils;

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
