package mut;

import java.util.List;
import java.util.Set;

/**
 * Executes a set of tests using a set of mutants. for each mutant, tests are executed until one fails, i.e. it killed the mutant.
 * The mutants which did not lead to a failed test are the survivers.
 * @author mike
 *
 */
public interface ITestExecuter {
	/**
	 * execute test set with the mutants provided
	 * @param testSet tests to be run
	 * @param mutantsToBeRun mutants to be run
	 * @return mutants that survived  - those that were not detected by a test
	 */
	Set<Mutant> executeTests(List<String> testSet, Set<Mutant> mutantsToBeRun);
}
