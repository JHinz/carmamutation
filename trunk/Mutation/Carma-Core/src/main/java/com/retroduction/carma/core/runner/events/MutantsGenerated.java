package com.retroduction.carma.core.runner.events;

import java.util.List;

import com.retroduction.carma.core.runner.Mutant;
import com.retroduction.carma.core.runner.utililties.ToStringUtils;
import com.retroduction.carma.core.transform.AbstractTransitionGroup;

public class MutantsGenerated implements IEvent {

	private List<Mutant> generatedMutants;

	private String classUnderTest;

	private AbstractTransitionGroup transitionGroup;

	public String getClassUnderTest() {
		return classUnderTest;
	}

	public List<Mutant> getGeneratedMutants() {
		return generatedMutants;
	}

	public AbstractTransitionGroup getTransitionGroup() {
		return transitionGroup;
	}

	public MutantsGenerated(List<Mutant> generateMutants, String classUnderTest, AbstractTransitionGroup transitionGroup) {
		super();
		this.generatedMutants = generateMutants;
		this.classUnderTest = classUnderTest;
		this.transitionGroup = transitionGroup;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}

}
