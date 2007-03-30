package com.mutation.events;

import java.util.Set;

import com.mutation.IClassSetResolver.ClassDescription;
import com.mutation.util.ToStringUtils;

public class ClassesUnderTestResolved implements IEvent {
	
	private Set<ClassDescription> classUnderTestNames;

	public Set<ClassDescription> getClassUnderTestNames() {
		return classUnderTestNames;
	}

	public ClassesUnderTestResolved(Set<ClassDescription> classUnderTestNames) {
		super();
		this.classUnderTestNames = classUnderTestNames;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
