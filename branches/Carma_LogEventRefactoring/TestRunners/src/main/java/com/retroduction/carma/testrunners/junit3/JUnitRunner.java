package com.retroduction.carma.testrunners.junit3;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.core.Core;
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

	private Logger logger = LoggerFactory.getLogger(JUnitRunner.class);

	private URL[] classesLocations = new URL[0];

	private URL[] testClassesLocations = new URL[0];

	private URL[] libraries = new URL[0];

	private boolean stopOnFirstFailedTest = true;

	private IMutantJUnitRunner runner;

	URL[] calculateCombinedClassPath() {
		URL[] urls = new URL[classesLocations.length + testClassesLocations.length + libraries.length];
		System.arraycopy(classesLocations, 0, urls, 0, classesLocations.length);
		System.arraycopy(testClassesLocations, 0, urls, classesLocations.length, testClassesLocations.length);
		System.arraycopy(libraries, 0, urls, classesLocations.length + testClassesLocations.length, libraries.length);
		return urls;
	}

	public void execute(Mutant mutant, Set<String> origTestNames) {

		mutant.setSurvived(true);

		URL[] urls = calculateCombinedClassPath();

		Set<String> executedTestsNames = new HashSet<String>();
		Set<String> killerTestNames = new TreeSet<String>();
		for (String testCase : origTestNames) {
			try {
				int failures = runner.perform(testCase, urls, mutant);
				executedTestsNames.add(testCase);
				if (failures > 0) {
					mutant.setSurvived(false);
					killerTestNames.add(testCase);
					if (stopOnFirstFailedTest) {
						logger.debug("Stopping on first failed test.");
						break;
					}
				}

			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}

		mutant.setExecutedTestsNames(executedTestsNames);
		mutant.setKillerTestNames(killerTestNames);

	}

	public Set<String> execute(Set<String> origTestNames) {

		URL[] urls = calculateCombinedClassPath();

		Set<String> brokenTestNames = new TreeSet<String>();
		for (String testCase : origTestNames) {
			try {

				if (runner.perform(testCase, urls, null) > 0) {
					brokenTestNames.add(testCase);
				}

			} catch (Exception e) {
				logger.debug("Found broken test: " + testCase, e);
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

}
