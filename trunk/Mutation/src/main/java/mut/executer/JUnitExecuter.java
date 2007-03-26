package mut.executer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestResult;
import mut.ITestExecuter;
import mut.Mutant;
import mut.executer.event.UnitTestFinished;
import mut.log.ConsoleEventLogger;
import mut.log.Event;
import mut.log.IEventLogger;
import mut.util.StopWatch;

import com.mutation.report.om.Mutation;
import com.mutation.report.om.MutationSet;
import com.mutation.report.om.ObjectFactory;
import com.mutation.report.om.SourceMapping;

/**
 * Executes mutation tests using junit tests
 * 
 * @author mike
 * 
 */
public class JUnitExecuter implements ITestExecuter {

	private IEventLogger logger = new ConsoleEventLogger(JUnitExecuter.class);

	private URL[] testClassesLocations;

	public List<Mutant> executeTests(MutationSet mutationSet, List<String> testSet, List<Mutant> mutantsToBeRun) {

		List<Mutant> survivors = new ArrayList<Mutant>();

		for (Mutant mutant : mutantsToBeRun) {

			for (String testCase : testSet) {
				if (runTest(mutationSet, testCase, mutant)) {
					survivors.add(mutant);
					break;
				}
			}

		}

		return survivors;
	}

	private boolean runTest(MutationSet mutationSet, String testCase, Mutant mutant) {
		StopWatch watch = new StopWatch();
		watch.start();
		MutantJUnitRunner runner = new MutantJUnitRunner(getTestClassesLocations(), mutant);

		Test suite = runner.getTest(testCase);
		TestResult result = runner.doRun(suite, false);
		int errors = result.errorCount();
		int failures = result.failureCount();

		UnitTestFinished stats = new UnitTestFinished();
		stats.setMutant(mutant);
		stats.setTestCase(testCase);
		stats.setTestResult(result);
		stats.setExecuTime(watch.stop());
		logger.log(Event.UNITTEST_FINISHED, stats);

		ObjectFactory factory = new ObjectFactory();

		Mutation op = factory.createMutation();
		op.setSurvived(!result.wasSuccessful());
		op.setName(mutant.getName());
		
		SourceMapping sourceMapping = new SourceMapping();
		sourceMapping.setClassName(mutant.getSourceMapping().getClassName());
		sourceMapping.setFile(mutant.getSourceMapping().getSourceFile());
		sourceMapping.setSourceLine(mutant.getSourceMapping().getLineNo());

		op.setSourceMapping(sourceMapping);
		mutationSet.getMutation().add(op);

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
		for (int i = 0; i < testClassesLocPaths.size(); i++) {
			urls[i] = testClassesLocPaths.get(i).toURL();
		}

		this.testClassesLocations = urls;
	}

	public void setLogger(IEventLogger logger) {
		this.logger = logger;
	}

}
