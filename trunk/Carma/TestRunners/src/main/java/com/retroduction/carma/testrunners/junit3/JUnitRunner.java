/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.testrunners.junit3;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.retroduction.carma.core.api.testrunners.ITestRunner;
import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

/**
 * Executes mutation tests using junit tests
 * 
 * @author mike
 * 
 */
public class JUnitRunner implements ITestRunner {

	private int timeout = 5000;

	private Logger logger = LoggerFactory.getLogger(JUnitRunner.class);

	private URL[] classesLocations = new URL[0];

	private URL[] testClassesLocations = new URL[0];

	private URL[] libraries = new URL[0];

	private boolean stopOnFirstFailedTest = true;

	private IMutantJUnitRunner runner;

	URL[] calculateCombinedClassPath() {
		URL[] urls = new URL[this.classesLocations.length + this.testClassesLocations.length + this.libraries.length];
		System.arraycopy(this.classesLocations, 0, urls, 0, this.classesLocations.length);
		System.arraycopy(this.testClassesLocations, 0, urls, this.classesLocations.length,
				this.testClassesLocations.length);
		System.arraycopy(this.libraries, 0, urls, this.classesLocations.length + this.testClassesLocations.length,
				this.libraries.length);
		return urls;
	}

	/**
	 * Run the test suite with mutation.
	 */
	public void execute(Mutant mutant, Set<String> origTestNames) {

		mutant.setSurvived(true);

		URL[] urls = this.calculateCombinedClassPath();

		Set<String> executedTestsNames = new HashSet<String>();
		Set<String> killerTestNames = new TreeSet<String>();
		for (String testCase : origTestNames) {
			try {

				this.runner.setMutant(mutant);
				this.runner.setTestCase(testCase);
				this.runner.setTestClassesLocation(urls);

				Object finishedSynchroLock = new Object();
				this.runner.setFinishedSynchroLock(finishedSynchroLock);

				boolean timeoutOccured = false;
				synchronized (finishedSynchroLock) {
					try {
						logger.debug("Starting mutation thread");
						Thread t = new Thread(this.runner);
						t.start();
						logger.debug("Waiting on results (allows to start and finish");
						finishedSynchroLock.wait(getTimeout());
						logger.debug("Waiting on mutation thread that it finishes its processing ");
						timeoutOccured = !this.runner.finished();
						if(timeoutOccured){
							logger.warn("Timeout occured: " + testCase);
						}
						logger.debug("Timeout occured: " + timeoutOccured);
						t.stop();

					} catch (InterruptedException e) {
					}
				}

				logger.debug("Not waiting anymore on finished log of mutation thread.");

				int failures = 0;

				if (timeoutOccured) {
					failures = 1; // Testcase hang interpreted as killed
					// testcase
					logger.debug("Failures 0 due to detected timeout");
				} else {
					failures = this.runner.getErrorCount();
					logger.debug("Failures valid due to not-detected timeout");
				}
				executedTestsNames.add(testCase);
				logger.debug("Failures occured: " + failures);
				if (failures > 0) {

					mutant.setSurvived(false);
					killerTestNames.add(testCase);
					if (this.stopOnFirstFailedTest) {
						this.logger.debug("Stopping on first failed test.");
						break;
					}
				} else {
					mutant.setSurvived(true);
				}

			} catch (Exception e) {
				this.logger.warn(e.getMessage());
			}
		}

		mutant.setExecutedTestsNames(executedTestsNames);
		mutant.setKillerTestNames(killerTestNames);

	}

	/**
	 * Run the test suite without mutation - just by using the original
	 * testcases.
	 */
	public Set<String> execute(Set<String> origTestNames) {

		URL[] urls = this.calculateCombinedClassPath();

		Set<String> brokenTestNames = new TreeSet<String>();
		for (String testCase : origTestNames) {
			try {

				Object finishedSynchroLock = new Object();
				this.runner.setFinishedSynchroLock(finishedSynchroLock);
				this.runner.setTestCase(testCase);
				this.runner.setTestClassesLocation(urls);

				boolean timeoutOccured = false;
				synchronized (finishedSynchroLock) {
					try {
						logger.debug("Starting mutation thread");
						Thread t = new Thread(this.runner);
						t.setContextClassLoader(Thread.currentThread().getContextClassLoader());
						t.start();
						logger.debug("Waiting on results (allows to start and finish");
						finishedSynchroLock.wait(getTimeout());
						logger.debug("Waiting on mutation thread that it finishes its processing ");
						timeoutOccured = !this.runner.finished();
						if(timeoutOccured){
							logger.warn("Timeout occured: " + testCase);
						}
						logger.debug("Timeout occured: " + timeoutOccured);
						t.stop();

					} catch (InterruptedException e) {
					}
				}

				logger.debug("Not waiting anymore on finished log of mutation thread.");

				int failures = 0;

				if (timeoutOccured) {
					failures = 1; // Testcase hang interpreted as killed
					// testcase
					logger.debug("Failures 0 due to detected timeout");
				} else {
					failures = this.runner.getErrorCount();
					logger.debug("Failures valid due to not-detected timeout");
				}

				if (failures > 0) {
					brokenTestNames.add(testCase);
				}

			} catch (Exception e) {
				this.logger.debug("Found broken test: " + testCase, e);
				brokenTestNames.add(testCase);
			}
		}
		return brokenTestNames;
	}

	public void setStopOnFirstFailedTest(boolean stopOnFirstFailedTest) {
		this.stopOnFirstFailedTest = stopOnFirstFailedTest;
	}

	public void setLibraries(URL[] libraries) {
		this.libraries = libraries;
	}

	public void setClassesLocations(URL[] classesLocations) {
		this.classesLocations = classesLocations;
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

	public void setClassesLocationsAsFiles(List<File> classesLocPaths) throws MalformedURLException {
		URL[] urls = new URL[classesLocPaths.size()];
		for (int i = 0; i < classesLocPaths.size(); i++) {
			urls[i] = classesLocPaths.get(i).toURL();
		}

		this.classesLocations = urls;
	}

	public void setRunner(IMutantJUnitRunner runner) {
		this.runner = runner;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	private int getTimeout() {
		return timeout;
	}

}
