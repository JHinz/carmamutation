package com.retroduction.carma.resolvers.util;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.core.api.testrunners.ITestCaseInstantiationVerifier;

public class TestCaseInstantiationVerifier implements ITestCaseInstantiationVerifier {

	private Log log = LogFactory.getLog(TestCaseInstantiationVerifier.class);

	private Set<File> classPath;

	private Set<File> testClassPath;

	private URLClassLoader loader;

	private void reinitPrivateClassLoader() {

		Set<File> combinedClassPathSet = new HashSet<File>();

		if (getClassPath() != null)
			combinedClassPathSet.addAll(getClassPath());

		if (getTestClassPath() != null)
			combinedClassPathSet.addAll(getTestClassPath());

		Set<URL> validURLs = filterInvalidURLs(combinedClassPathSet);

		setLoader(new URLClassLoader((URL[]) validURLs.toArray(new URL[0]), this.getClass().getClassLoader()));
	}

	Set<URL> filterInvalidURLs(Set<File> classPathEntries) {

		Set<URL> result = new HashSet<URL>();

		if (classPathEntries != null) {
			for (File file : classPathEntries) {
				if (file != null) {
					try {
						result.add(file.toURL());
					} catch (MalformedURLException e) {
						log.warn("Invalid class path entry: " + file.toString());
					}
				}
			}
		}
		return result;
	}

	private URLClassLoader getLoader() {
		return loader;
	}

	private void setLoader(URLClassLoader loader) {
		this.loader = loader;
	}

	private Set<File> getClassPath() {
		return classPath;
	}

	public void setClassPath(Set<File> classesPath) {
		this.classPath = classesPath;
		reinitPrivateClassLoader();
	}

	private Set<File> getTestClassPath() {
		return testClassPath;
	}

	public void setTestClassPath(Set<File> testClassesPath) {
		this.testClassPath = testClassesPath;
		reinitPrivateClassLoader();
	}

	public HashSet<String> removeNonInstantiatableClasses(Set<String> fqClassNames) {

		HashSet<String> usableTestClassDescriptions = new HashSet<String>();

		for (String testClassDescription : fqClassNames) {

			try {

				Class testClass = getLoader().loadClass(testClassDescription);

				if (Modifier.isAbstract(testClass.getModifiers()) || Modifier.isInterface(testClass.getModifiers())) {
					log.info("Skipping abstract class or interface in test set:" + testClassDescription);
					continue;
				}

			} catch (Exception e) {
				log.warn("Skipping class in test set due to class loading problem:" + testClassDescription);
				continue;
			}

			usableTestClassDescriptions.add(testClassDescription);
		}

		return usableTestClassDescriptions;
	}

}
