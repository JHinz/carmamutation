package com.retroduction.carma.testrunners.junit3;

import java.net.URL;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.runner.BaseTestRunner;
import junit.runner.TestSuiteLoader;

import com.retroduction.carma.core.MutationClassLoader;
import com.retroduction.carma.core.api.testrunners.om.Mutant;

/**
 * JUnit Runner for mutation tests. Uses specific class loeader to load thze
 * mutants
 * 
 * @author mike
 * 
 */
public class MutantJUnitRunner extends BaseTestRunner implements IMutantJUnitRunner {
	
	MyTestSuiteLoader loader;

	@Override
	public TestSuiteLoader getLoader() {
		return loader;
	}

	private ClassLoader mutantLoader;

	private ClassLoader replacedClassLoader;

	private void overrideClassLoader(URL[] testClassesLocation, Mutant mutant) {
		if (mutant != null) {
			mutantLoader = new MutationClassLoader(testClassesLocation, mutant.getClassName(), mutant.getByteCode(),
					Thread.currentThread().getContextClassLoader());
		} else {
			mutantLoader = new MutationClassLoader(testClassesLocation, null, null, Thread.currentThread()
					.getContextClassLoader());
		}
		loader = new MyTestSuiteLoader();
		replacedClassLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(mutantLoader);
	}

	private void restoreReplacedClassLoader() {
		Thread.currentThread().setContextClassLoader(replacedClassLoader);
	}

	private int runTests(String testCase) {
		try {
			Test suite = getTest(testCase);
			TestResult result = doRun(suite, false);

			int errors = result.errorCount();
			int failures = result.failureCount();
			return errors + failures;
		} catch (RuntimeException e) {
			restoreReplacedClassLoader();
			throw e;
		}
	}

	public int perform(String testCase, URL[] testClassesLocation, Mutant mutant) {
		overrideClassLoader(testClassesLocation, mutant);
		int errorCount = runTests(testCase);
		restoreReplacedClassLoader();
		return errorCount;

	}

	private class MyTestSuiteLoader implements TestSuiteLoader {
		public Class load(String suiteClassName) throws ClassNotFoundException {
			return mutantLoader.loadClass(suiteClassName);
		}

		public Class reload(Class aClass) throws ClassNotFoundException {
			throw new UnsupportedOperationException("Not implemented");
		}
	}

	private TestResult doRun(Test suite, boolean wait) {
		TestResult result = createTestResult();
		suite.run(result);
		return result;
	}

	/**
	 * Creates the TestResult to be used for the test run.
	 */
	protected TestResult createTestResult() {
		return new TestResult();
	}

	@Override
	protected void runFailed(String message) {
		throw new RuntimeException(message);
	}

	@Override
	public void testEnded(String testName) {
	}

	@Override
	public void testFailed(int status, Test test, Throwable t) {
	}

	@Override
	public void testStarted(String testName) {
	}

}
