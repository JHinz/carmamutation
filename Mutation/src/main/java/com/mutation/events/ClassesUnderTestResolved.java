package com.mutation.events;

import java.util.Set;

import mut.util.ToStringUtils;

public class ClassesUnderTestResolved implements IEvent {
	private Set<String> classUnderTestNames;

	public Set<String> getClassUnderTestNames() {
		return classUnderTestNames;
	}

	public ClassesUnderTestResolved(Set<String> classUnderTestNames) {
		super();
		this.classUnderTestNames = classUnderTestNames;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
