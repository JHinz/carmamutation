package com.mutation.runner.events;

import java.util.List;

import com.mutation.runner.Mutant;
import com.mutation.runner.utililties.ToStringUtils;
import com.mutation.transform.AbstractTransitionGroup;

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
