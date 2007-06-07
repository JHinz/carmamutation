package com.retroduction.carma.core.transform;

import java.util.ArrayList;
import java.util.List;

import com.retroduction.carma.core.runner.Mutant;
import com.retroduction.carma.core.runner.events.IEventListener;

public class MutantGenerator implements IMutationGenerator{

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassByteCode,
			List<AbstractTransitionGroup> transitionGroups, IEventListener listener) {

		List<Mutant> result = new ArrayList<Mutant>();

		for (AbstractTransitionGroup group : transitionGroups) {

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
