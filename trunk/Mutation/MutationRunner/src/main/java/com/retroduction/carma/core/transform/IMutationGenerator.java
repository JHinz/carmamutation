package com.retroduction.carma.core.transform;

import java.util.List;

import com.retroduction.carma.core.runner.Mutant;
import com.retroduction.carma.core.runner.events.IEventListener;

public interface IMutationGenerator {

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassByteCode,
			List<AbstractTransitionGroup> transitionGroups, IEventListener listener);

}
