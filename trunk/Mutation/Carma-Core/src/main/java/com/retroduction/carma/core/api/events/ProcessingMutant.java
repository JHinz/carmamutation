package com.retroduction.carma.core.api.events;

import com.retroduction.carma.core.api.testrunners.Mutant;
import com.retroduction.carma.utilities.ToStringUtils;

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
