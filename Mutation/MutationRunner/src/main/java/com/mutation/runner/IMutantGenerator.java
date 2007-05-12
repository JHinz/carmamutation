package com.mutation.runner;

import java.util.List;

import com.mutation.runner.events.IEventListener;
import com.mutation.transform.AbstractTransitionGroup;

/**
 * Generator interface to generate a set of mutants
 * 
 * @author mike
 * 
 */
public interface IMutantGenerator {

	/**
	 * Generate mutants for the class under test
	 * 
	 * @param classUnderTest
	 * @param operators
	 *            the mutationOperators to be applied
	 * @return set of mutants
	 */

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassByteCode,
			List<AbstractTransitionGroup> transitionGroups, IEventListener listener);

}
