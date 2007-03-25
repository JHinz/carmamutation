package mut.executer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestResult;
import mut.ITestExecuter;
import mut.Mutant;
import mut.executer.event.UnitTestFinished;
import mut.log.ConsoleEventLogger;
import mut.log.Event;
import mut.log.IEventLogger;
import mut.util.StopWatch;

/**
 * Executes mutation tests using junit tests
 * @author mike
 *
 */
public class JUnitExecuter implements ITestExecuter {
	private IEventLogger logger = new ConsoleEventLogger(JUnitExecuter.class);
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
		StopWatch watch = new StopWatch();
		watch.start();
		MutantJUnitRunner runner = new MutantJUnitRunner(getTestClassesLocations(), mutant);
		
		Test suite= runner.getTest(testCase);
		TestResult result = runner.doRun(suite, false);
		int errors = result.errorCount();
		int failures = result.failureCount();

		UnitTestFinished stats = new UnitTestFinished();
		stats.setMutant(mutant);
		stats.setTestCase(testCase);
		stats.setTestResult(result);
		stats.setExecuTime(watch.stop());
		logger.log(Event.UNITTEST_FINISHED, stats);
		return (errors + failures) == 0;
	}

	public URL[] getTestClassesLocations() {
		return testClassesLocations;
	}

	public void setTestClassesLocations(URL[] testClassesLocation) {
		this.testClassesLocations = testClassesLocation;
	}

	public void setTestClassesLocationsAsFiles(List<File> testClassesLocPaths) throws MalformedURLException {
		URL[] urls = new URL[testClassesLocPaths.size()];
		for(int i=0; i < testClassesLocPaths.size(); i++){
			urls[i] = testClassesLocPaths.get(i).toURL();
		}
		
		this.testClassesLocations = urls;
	}

	public void setLogger(IEventLogger logger) {
		this.logger = logger;
	}


}
