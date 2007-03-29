package com.mutation.events;

import com.mutation.Mutant;
import com.mutation.util.ToStringUtils;

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
