package com.mutation.runner.events;

import java.util.List;

import com.mutation.runner.IClassSetResolver.ClassDescription;
import com.mutation.runner.utililties.ToStringUtils;

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
