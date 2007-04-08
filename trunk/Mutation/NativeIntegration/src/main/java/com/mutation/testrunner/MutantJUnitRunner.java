package com.mutation.testrunner;

import java.net.URL;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.runner.BaseTestRunner;
import junit.runner.TestSuiteLoader;

import com.mutation.runner.Mutant;

/**
 * JUnit Runner for mutation tests. Uses specific class loeader to load thze mutants
 * @author mike
 *
 */
public class MutantJUnitRunner extends BaseTestRunner {
	MyTestSuiteLoader loader;

	@Override
	public TestSuiteLoader getLoader() {
		return loader;
	}

	private ClassLoader mutantLoader;

	public MutantJUnitRunner(URL[] testClassesLocation, Mutant mutant) {

		mutantLoader = new MutationClassLoader(testClassesLocation, mutant
				.getClassName(), mutant.getByteCode());
		loader = new MyTestSuiteLoader();
	}

	class MyTestSuiteLoader implements TestSuiteLoader {
		public Class load(String suiteClassName) throws ClassNotFoundException {
			return mutantLoader.loadClass(suiteClassName);
		}

		public Class reload(Class aClass) throws ClassNotFoundException {
			throw new UnsupportedOperationException("Not implemented");
		}
	}

	
	public TestResult doRun(Test suite, boolean wait) {
		TestResult result= createTestResult();
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
		// TODO Auto-generated method stub
		
	}


	@Override
	public void testFailed(int status, Test test, Throwable t) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void testStarted(String testName) {
		// TODO Auto-generated method stub
		
	}	

}
