package com.retroduction.carma.core.api.transitions.events;

import com.retroduction.carma.utilities.ToStringUtils;

public class ProcessingMutationOperator implements IEvent {
	private String operatorName;

	public String getOperatorName() {
		return operatorName;
	}

	public ProcessingMutationOperator(String classUnderTest) {
		super();
		this.operatorName = classUnderTest;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
