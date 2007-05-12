package com.mutation.runner.events;

import java.util.List;

import com.mutation.runner.Mutant;
import com.mutation.runner.utililties.ToStringUtils;

public class TestsExecuted implements IEvent {

	private Mutant mutant;

	private List<String> testNames;

	private boolean mutantSurvived;

	private List<String> killerTestNames;

	public TestsExecuted(Mutant mutant, List<String> testNames, boolean mutantSurvived, List<String> killerTestNames) {
		super();
		this.mutant = mutant;
		this.testNames = testNames;
		this.mutantSurvived = mutantSurvived;
		this.killerTestNames = killerTestNames;
	}

	public List<String> getKillerTestNames() {
		return killerTestNames;
	}

	public Mutant getMutant() {
		return mutant;
	}

	public boolean isMutantSurvived() {
		return mutantSurvived;
	}

	public List<String> getTestNames() {
		return testNames;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
