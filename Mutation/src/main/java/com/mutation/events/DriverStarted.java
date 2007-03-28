package com.mutation.events;

import java.util.List;

import com.mutation.EMutationOperator;

public class DriverStarted implements IEvent {
	private List<EMutationOperator> operators;

	public List<EMutationOperator> getOperators() {
		return operators;
	}

	public DriverStarted(List<EMutationOperator> operators) {
		super();
		this.operators = operators;
	}
}
