package com.retroduction.carma.core.runner.events;

import com.retroduction.carma.core.runner.Mutant;
import com.retroduction.carma.core.runner.utililties.ToStringUtils;

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
