package com.retroduction.carma.core;

import java.util.ArrayList;
import java.util.List;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.transitions.IMutationGenerator;
import com.retroduction.carma.core.api.transitions.ITransition;
import com.retroduction.carma.core.api.transitions.ITransitionGroup;
import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

public class MutantGenerator implements IMutationGenerator {

	private Logger logger = LoggerFactory.getLogger(MutantGenerator.class);

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassByteCode,
			ITransitionGroup transitionGroup) {

		List<Mutant> result = new ArrayList<Mutant>();

		this.logger.debug("Processing transition group: " + transitionGroup.getTransitions());

		for (ITransition transition : transitionGroup.getTransitions()) {

			this.logger.debug("Using transition <" + transition.getName() + " for mutation process...");

			List<Mutant> mutants = transition.applyTransitions(originalClassByteCode);

			this.logger.debug("Number of hits during last mutation process step: " + mutants.size());

			for (Mutant mutant : mutants) {
				mutant.setClassName(classUnderTest);
			}

			result.addAll(mutants);
		}

		return result;
	}
}
