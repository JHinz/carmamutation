package com.mutation.transform;

import java.util.List;

import com.mutation.runner.Mutant;
import com.mutation.runner.events.IEventListener;

public interface IMutationGenerator {

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassByteCode,
			List<AbstractTransitionGroup> transitionGroups, IEventListener listener);

}
