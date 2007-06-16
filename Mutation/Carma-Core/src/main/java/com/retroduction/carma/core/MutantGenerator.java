package com.retroduction.carma.core;

import java.util.ArrayList;
import java.util.List;

import com.retroduction.carma.core.api.eventlisteners.IEventListener;
import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.transitions.IMutationGenerator;
import com.retroduction.carma.core.api.transitions.ITransition;
import com.retroduction.carma.core.api.transitions.ITransitionGroup;

public class MutantGenerator implements IMutationGenerator{

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassByteCode,
			List<ITransitionGroup> transitionGroups, IEventListener listener) {

		List<Mutant> result = new ArrayList<Mutant>();

		for (ITransitionGroup group : transitionGroups) {

			for (ITransition transition : group.getTransitions()) {

				List<Mutant> mutants = transition.applyTransitions(originalClassByteCode, listener);

				for (Mutant mutant : mutants) {
					mutant.setClassName(classUnderTest);
					mutant.setTransitionGroup(group);
				}

				result.addAll(mutants);
			}

		}

		return result;
	}
}
