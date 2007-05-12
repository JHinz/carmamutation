package com.mutation.testrunner.junit3;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestResult;

import com.mutation.runner.Mutant;
import com.mutation.runner.events.IEventListener;
import com.mutation.runner.events.TestNotExecuted;
import com.mutation.runner.events.TestsExecuted;
import com.mutation.testrunner.ITestRunner;
import com.mutation.testrunner.MutantJUnitRunner;

/**
 * Executes mutation tests using junit tests
 * 
 * @author mike
 * 
 */
public class JUnitRunner implements ITestRunner {

	private URL[] testClassesLocations;

	private boolean stopOnFirstFailedTest = true;

	/**
	 * 
	 * @return sum of failures and errors
	 */
	private int runTest(String testCase, Mutant mutant) {

		MutantJUnitRunner runner = new MutantJUnitRunner(getTestClassesLocations(), mutant);
		Test suite = runner.getTest(testCase);
		TestResult result = runner.doRun(suite, false);
		int errors = result.errorCount();
		int failures = result.failureCount();
		return errors + failures;
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

	public void execute(Mutant mutant, List<String> testNames, IEventListener eventListener) {
		boolean survived = true;
		List<String> killerTestNames = new ArrayList<String>();
		for (String testCase : testNames) {
			try {
				int failures = runTest(testCase, mutant);
				if (failures > 0) {
					survived = false;
					killerTestNames.add(testCase);
					mutant.setSurvived(false);
					if (stopOnFirstFailedTest) {
						break;
					}
				}

			} catch (Exception e) {
				eventListener.notifyEvent(new TestNotExecuted(mutant, testCase, e));
			}
		}
		eventListener.notifyEvent(new TestsExecuted(mutant, testNames, survived, killerTestNames));
	}

	public void setStopOnFirstFailedTest(boolean stopOnFirstFailedTest) {
		this.stopOnFirstFailedTest = stopOnFirstFailedTest;
	}

}
