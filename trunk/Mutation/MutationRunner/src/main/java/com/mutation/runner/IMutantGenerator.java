package com.mutation.runner;

import java.util.List;

import com.mutation.runner.events.IEventListener;

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

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassBytecode, EMutationOperator operator, IEventListener eventListener);
}