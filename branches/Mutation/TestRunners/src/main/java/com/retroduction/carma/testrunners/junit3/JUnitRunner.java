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

	private Logger logger = LoggerFactory.getLogger(JUnitRunner.class);

	private URL[] classesLocations = new URL[0];

	private URL[] testClassesLocations = new URL[0];

	private URL[] libraries = new URL[0];

	private boolean stopOnFirstFailedTest = true;

	private IMutantJUnitRunner runner;

	URL[] calculateCombinedClassPath() {
		URL[] urls = new URL[this.classesLocations.length + this.testClassesLocations.length + this.libraries.length];
		System.arraycopy(this.classesLocations, 0, urls, 0, this.classesLocations.length);
		System.arraycopy(this.testClassesLocations, 0, urls, this.classesLocations.length, this.testClassesLocations.length);
		System.arraycopy(this.libraries, 0, urls, this.classesLocations.length + this.testClassesLocations.length, this.libraries.length);
		return urls;
	}

	public void execute(Mutant mutant, Set<String> origTestNames) {

		mutant.setSurvived(true);

		URL[] urls = this.calculateCombinedClassPath();

		Set<String> executedTestsNames = new HashSet<String>();
		Set<String> killerTestNames = new TreeSet<String>();
		for (String testCase : origTestNames) {
			try {
				int failures = this.runner.perform(testCase, urls, mutant);
				executedTestsNames.add(testCase);
				if (failures > 0) {
					mutant.setSurvived(false);
					killerTestNames.add(testCase);
					if (this.stopOnFirstFailedTest) {
						this.logger.debug("Stopping on first failed test.");
						break;
					}
				}

			} catch (Exception e) {
				this.logger.warn(e.getMessage());
			}
		}

		mutant.setExecutedTestsNames(executedTestsNames);
		mutant.setKillerTestNames(killerTestNames);

	}

	public Set<String> execute(Set<String> origTestNames) {

		URL[] urls = this.calculateCombinedClassPath();

		Set<String> brokenTestNames = new TreeSet<String>();
		for (String testCase : origTestNames) {
			try {

				if (this.runner.perform(testCase, urls, null) > 0) {
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

}
