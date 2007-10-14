package com.retroduction.carma.core.api.eventlisteners.om;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.utilities.ToStringUtils;

public class ProcessingMutationOperator implements IEvent {
	
	private String transitionGroupName;

	public String getTransitionGroupName() {
		return this.transitionGroupName;
	}

	public ProcessingMutationOperator(String classUnderTest) {
		super();
		this.transitionGroupName = classUnderTest;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
