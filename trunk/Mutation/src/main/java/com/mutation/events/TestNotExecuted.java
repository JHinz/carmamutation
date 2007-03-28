package com.mutation.events;

import com.mutation.Mutant;
import com.mutation.util.ToStringUtils;

public class TestNotExecuted implements IEvent {
	private Mutant mutant; 
	private String testCaseName; 
	private Exception error;
	public TestNotExecuted(Mutant mutant, String testCaseName, Exception error) {
		super();
		this.testCaseName = testCaseName;
		this.mutant = mutant;
		this.error = error;
	}
	public Exception getError() {
		return error;
	}
	public Mutant getMutant() {
		return mutant;
	}
	public String getTestCaseName() {
		return testCaseName;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
