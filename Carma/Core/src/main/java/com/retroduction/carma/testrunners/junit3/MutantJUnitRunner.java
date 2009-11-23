/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */
package com.retroduction.carma.testrunners.junit3;

import java.net.URL;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.runner.BaseTestRunner;
import junit.runner.TestSuiteLoader;

import com.retroduction.carma.core.MutationClassLoader;
import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

/**
 * JUnit Runner for mutation tests. Uses specific class loeader to load thze
 * mutants
 * 
 * @author mike
 * 
 */
public class MutantJUnitRunner extends BaseTestRunner implements IMutantJUnitRunner {

	private Logger logger = LoggerFactory.getLogger(MutantJUnitRunner.class);

	MyTestSuiteLoader loader;

	@Override
	public TestSuiteLoader getLoader() {
		return this.loader;
	}

	private ClassLoader mutantLoader;

	private ClassLoader replacedClassLoader;

	private String testCase;

	private URL[] testClassesLocation;

	private Mutant mutant;

	private int errorCount;

	private boolean finished = false;

	private Object lock;

	public void setMutant(Mutant mutant) {
		this.mutant = mutant;
	}

	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}

	public void setTestClassesLocation(URL[] testClassesLocation) {
		this.testClassesLocation = testClassesLocation;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public boolean finished() {
		return finished;
	}

	public void setFinishedSynchroLock(Object lock) {
		this.lock = lock;
	}

	private void overrideClassLoader(URL[] testClassesLocation, Mutant mutant) {

		this.logger.debug("Injecting carma classloader");

		if (mutant != null) {
			this.mutantLoader = new MutationClassLoader(testClassesLocation, mutant.getClassName(), mutant
					.getByteCode(), Thread.currentThread().getContextClassLoader());
		} else {
			this.mutantLoader = new MutationClassLoader(testClassesLocation, null, null, Thread.currentThread()
					.getContextClassLoader());
		}
		this.loader = new MyTestSuiteLoader();
		this.replacedClassLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(this.mutantLoader);
	}

	private void restoreReplacedClassLoader() {
		this.logger.debug("Restoring original classloader");
		Thread.currentThread().setContextClassLoader(this.replacedClassLoader);
	}

	private int runTest(String testCase) {
		
		int errors = 0;
		int failures = 0;
		
		try {
			Test suite = this.getTest(testCase);
			TestResult result = this.doRun(suite);

			errors+= result.errorCount();
			failures+= result.failureCount();
			return errors + failures;
		} catch (Throwable e) {
			this.restoreReplacedClassLoader();
			logger.warn(e);
			return errors + failures + 1;
		}
	}

	/**
	 * Non-threaded execution of runner.
	 */
	public int perform(String testCase, URL[] testClassesLocation, Mutant mutant) {
		try {
			this.overrideClassLoader(testClassesLocation, mutant);
			int errorCount = this.runTest(testCase);
			this.restoreReplacedClassLoader();
			return errorCount;
		} catch (RuntimeException e) {
			// don't throw exceptions when using threaded api - see interface description !
			return 1;
		}
	}

	/**
	 * Threaded execution of runner.
	 * 
	 */
	public void run() {
		synchronized (this.lock) {
		}
		this.overrideClassLoader(testClassesLocation, mutant);
		errorCount = this.runTest(testCase);
		this.restoreReplacedClassLoader();
		this.finished = true;
		synchronized (this.lock) {
			this.lock.notify();
		}
	}

	private class MyTestSuiteLoader implements TestSuiteLoader {
		public Class<?> load(String suiteClassName) throws ClassNotFoundException {
			return MutantJUnitRunner.this.mutantLoader.loadClass(suiteClassName);
		}

		public Class<?> reload(Class aClass) throws ClassNotFoundException {
			throw new UnsupportedOperationException("Not implemented");
		}
	}

	private TestResult doRun(Test suite) {
		TestResult result = this.createTestResult();
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
