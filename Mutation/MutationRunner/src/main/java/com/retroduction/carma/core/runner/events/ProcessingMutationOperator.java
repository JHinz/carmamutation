package com.retroduction.carma.core.runner.events;

import com.retroduction.carma.core.runner.utililties.ToStringUtils;

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
