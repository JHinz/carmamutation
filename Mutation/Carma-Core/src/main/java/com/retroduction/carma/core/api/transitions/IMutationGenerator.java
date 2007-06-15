package com.retroduction.carma.core.api.transitions;

import java.util.List;

import com.retroduction.carma.core.api.transitions.events.IEventListener;
import com.retroduction.carma.core.runner.Mutant;

public interface IMutationGenerator {

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassByteCode,
			List<ITransitionGroup> transitionGroups, IEventListener listener);

}
