package com.retroduction.carma.core.transform;

import java.util.ArrayList;
import java.util.List;

import com.retroduction.carma.core.runner.Mutant;
import com.retroduction.carma.core.runner.SourceCodeMapping;
import com.retroduction.carma.core.runner.events.IEventListener;

/**
 * Fake mutant generator which does not create real mutants. Instead it takes
 * the passed class code and creates a mutant object which holds the unmodified
 * byte code.
 * 
 * This class is useful for determination whether certain or all tests fail
 * regardless of created any mutations applied.
 * 
 * @author arau
 * 
 */
public class DummyMutantGenerator implements IMutationGenerator {

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassByteCode,
			List<AbstractTransitionGroup> transitionGroups, IEventListener listener) {

		List<Mutant> result = new ArrayList<Mutant>();

		Mutant mutant = new Mutant();
		mutant.setClassName(classUnderTest);
		mutant.setName("Dummy Mutation");
		mutant.setByteCode(originalClassByteCode);
		mutant.setSourceMapping(new SourceCodeMapping());

		result.add(mutant);

		return result;
	}
}
