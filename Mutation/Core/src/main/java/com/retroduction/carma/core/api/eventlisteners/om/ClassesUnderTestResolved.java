package com.retroduction.carma.core.api.eventlisteners.om;

import java.util.Set;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.om.TestedClassInfo;
import com.retroduction.carma.utilities.ToStringUtils;

public class ClassesUnderTestResolved implements IEvent {

	private Set<TestedClassInfo> classUnderTestNames;

	public Set<TestedClassInfo> getClassUnderTestNames() {
		return classUnderTestNames;
	}

	public ClassesUnderTestResolved(Set<TestedClassInfo> classUnderTestNames) {
		super();
		this.classUnderTestNames = classUnderTestNames;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
