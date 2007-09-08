package com.retroduction.carma.core.api.transitions;

import java.util.List;
import java.util.Set;

import com.retroduction.carma.core.api.testrunners.om.Mutant;

public interface IMutationGenerator {

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassByteCode,
			ITransitionGroup transitionGroup);

}
