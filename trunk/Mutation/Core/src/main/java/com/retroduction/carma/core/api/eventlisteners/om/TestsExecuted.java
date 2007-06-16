package com.retroduction.carma.core.api.eventlisteners.om;

import java.util.Set;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.utilities.ToStringUtils;

public class TestsExecuted implements IEvent {

	private Mutant mutant;

	private Set<String> testNames;

	private boolean mutantSurvived;

	private Set<String> killerTestNames;

	public TestsExecuted(Mutant mutant, Set<String> testNames, boolean mutantSurvived, Set<String> killerTestNames) {
		super();
		this.mutant = mutant;
		this.testNames = testNames;
		this.mutantSurvived = mutantSurvived;
		this.killerTestNames = killerTestNames;
	}

	public Set<String> getKillerTestNames() {
		return killerTestNames;
	}

	public Mutant getMutant() {
		return mutant;
	}

	public boolean isMutantSurvived() {
		return mutantSurvived;
	}

	public Set<String> getTestNames() {
		return testNames;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
