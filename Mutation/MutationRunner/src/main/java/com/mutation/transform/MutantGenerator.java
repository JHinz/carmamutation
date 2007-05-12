package com.mutation.transform;

import java.util.ArrayList;
import java.util.List;

import com.mutation.runner.IMutantGenerator;
import com.mutation.runner.Mutant;
import com.mutation.runner.events.IEventListener;

public class MutantGenerator implements IMutantGenerator {

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassByteCode,
			List<AbstractTransitionGroup> transitionGroups, IEventListener listener) {

		List<Mutant> result = new ArrayList<Mutant>();

		for (AbstractTransitionGroup group : transitionGroups) {

			for (ITransition transition : group.getTransitions()) {

				List<Mutant> mutants = transition.applyTransitions(originalClassByteCode, listener);
				
				for ( Mutant mutant : mutants) {
					mutant.setClassName(classUnderTest);
					mutant.setTransitionGroup(group);
				}

				result.addAll(mutants);
			}

		}

		return result;
	}
}
