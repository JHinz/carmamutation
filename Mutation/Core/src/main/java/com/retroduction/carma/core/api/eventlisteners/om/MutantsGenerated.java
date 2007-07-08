package com.retroduction.carma.core.api.eventlisteners.om;

import java.util.List;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.transitions.ITransitionGroup;
import com.retroduction.carma.utilities.ToStringUtils;

public class MutantsGenerated implements IEvent {

	private List<Mutant> generatedMutants;

	private String classUnderTest;

	private ITransitionGroup transitionGroup;

	public String getClassUnderTest() {
		return this.classUnderTest;
	}

	public List<Mutant> getGeneratedMutants() {
		return this.generatedMutants;
	}

	public ITransitionGroup getTransitionGroup() {
		return this.transitionGroup;
	}

	public MutantsGenerated(List<Mutant> generateMutants, String classUnderTest, ITransitionGroup transitionGroup) {
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
