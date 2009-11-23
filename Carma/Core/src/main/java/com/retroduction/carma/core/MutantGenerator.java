/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */
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
