package mut;

import java.util.List;

import com.mutation.report.om.MutationSet;

/**
 * Executes a set of tests using a set of mutants. for each mutant, tests are
 * executed until one fails, i.e. it killed the mutant. The mutants which did
 * not lead to a failed test are the survivors.
 * 
 * @author mike
 * 
 */
public interface ITestExecuter {
	/**
	 * execute test set with the mutants provided
	 * 
	 * @param testSet
	 *            tests to be run
	 * @param mutantsToBeRun
	 *            mutants to be run
	 * @return mutants that survived - those that were not detected by a test
	 */
	List<Mutant> executeTests(MutationSet mutationSet, List<String> testSet, List<Mutant> mutantsToBeRun);
}
