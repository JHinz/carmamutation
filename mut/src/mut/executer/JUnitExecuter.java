package mut.executer;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestResult;
import mut.ITestExecuter;
import mut.Mutant;

/**
 * Executes mutation tests using junit tests
 * @author mike
 *
 */
public class JUnitExecuter implements ITestExecuter {
	private URL[] testClassesLocations;
	
	public Set<Mutant> executeTests(List<String> testSet,
			Set<Mutant> mutantsToBeRun) {

		Set<Mutant> survivors = new HashSet<Mutant>();

		// for each mutant
		for(Mutant mutant : mutantsToBeRun){
			// execute tests until killed
			boolean killed = tryKillMutant(testSet, mutant);
			if(!killed){
				survivors.add(mutant);
			}
		}
		

		return survivors;
	}

	private boolean tryKillMutant(List<String> testSet, Mutant mutant) {
		for(String testCase : testSet){
			boolean success = runTest(testCase, mutant);
			if(!success){
				return true;
			}
		}

		return false;
	}
	
	private boolean runTest(String testCase, Mutant mutant) {
		
		MutantJUnitRunner runner = new MutantJUnitRunner(getTestClassesLocations(), mutant);
		
		Test suite= runner.getTest(testCase);
		TestResult result = runner.doRun(suite, false);
		int errors = result.errorCount();
		int failures = result.failureCount();

		return (errors + failures) == 0;
	}

	public URL[] getTestClassesLocations() {
		return testClassesLocations;
	}

	public void setTestClassesLocations(URL[] testClassesLocation) {
		this.testClassesLocations = testClassesLocation;
	}



}
