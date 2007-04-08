package com.mutation.runner.events;

import com.mutation.runner.Mutant;
import com.mutation.runner.utililties.ToStringUtils;

public class ProcessingMutant implements IEvent {

	private Mutant mutant;

	public Mutant getMutant() {
		return mutant;
	}

	public ProcessingMutant(Mutant mutant) {
		super();
		this.mutant = mutant;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
