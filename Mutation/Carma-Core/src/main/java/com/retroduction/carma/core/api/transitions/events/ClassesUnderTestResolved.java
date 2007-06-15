package com.retroduction.carma.core.api.transitions.events;

import java.util.List;

import com.retroduction.carma.core.runner.ClassDescription;
import com.retroduction.carma.utilities.ToStringUtils;

public class ClassesUnderTestResolved implements IEvent {

	private List<ClassDescription> classUnderTestNames;

	public List<ClassDescription> getClassUnderTestNames() {
		return classUnderTestNames;
	}

	public ClassesUnderTestResolved(List<ClassDescription> classUnderTestNames) {
		super();
		this.classUnderTestNames = classUnderTestNames;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
