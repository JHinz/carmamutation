package com.retroduction.carma.core.api.eventlisteners.om;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.utilities.ToStringUtils;

public class TestsExecuted implements IEvent {

	private Mutant mutant;

	public TestsExecuted(Mutant mutant) {
		super();
		this.mutant = mutant;
	}

	public Mutant getMutant() {
		return this.mutant;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
