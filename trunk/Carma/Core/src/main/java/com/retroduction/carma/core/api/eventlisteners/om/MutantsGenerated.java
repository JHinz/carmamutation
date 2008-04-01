/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
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
