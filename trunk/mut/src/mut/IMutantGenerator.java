package mut;

import java.util.Set;

/**
 * Generator interface to generate a set of mutants
 * @author mike
 *
 */
public interface IMutantGenerator {
	/**
	 * Generate mutants for the class under test
	 * @param classUnderTest
	 * @param operators the mutationOperators to be applied
	 * @return set of mutants
	 */
	
	public Set<Mutant> generateMutants(String classUnderTest, Set<MutationOperator> operators);
}
