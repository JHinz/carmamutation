package com.mutation.runner.events;

import java.util.List;

import com.mutation.runner.EMutationOperator;
import com.mutation.runner.utililties.ToStringUtils;

public class DriverStarted implements IEvent {
	private List<EMutationOperator> operators;

	public List<EMutationOperator> getOperators() {
		return operators;
	}

	public DriverStarted(List<EMutationOperator> operators) {
		super();
		this.operators = operators;
	}
	
	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
