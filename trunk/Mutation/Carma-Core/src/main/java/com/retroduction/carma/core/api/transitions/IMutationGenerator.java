package com.retroduction.carma.core.api.transitions;

import java.util.List;

import com.retroduction.carma.core.api.events.IEventListener;
import com.retroduction.carma.core.api.testrunners.Mutant;

public interface IMutationGenerator {

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassByteCode,
			List<ITransitionGroup> transitionGroups, IEventListener listener);

}
