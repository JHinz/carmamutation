/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
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

		if (this.getClassPath() != null) {
			combinedClassPathSet.addAll(this.getClassPath());
		}

		if (this.getTestClassPath() != null) {
			combinedClassPathSet.addAll(this.getTestClassPath());
		}

		Set<URL> validURLs = this.filterInvalidURLs(combinedClassPathSet);

		this.setLoader(new URLClassLoader(validURLs.toArray(new URL[0]), this.getClass().getClassLoader()));
	}

	Set<URL> filterInvalidURLs(Set<File> classPathEntries) {

		Set<URL> result = new HashSet<URL>();

		if (classPathEntries != null) {
			for (File file : classPathEntries) {
				if (file != null) {
					try {
						result.add(file.toURL());
					} catch (MalformedURLException e) {
						this.log.warn("Invalid class path entry: " + file.toString());
					}
				}
			}
		}
		return result;
	}

	private URLClassLoader getLoader() {
		return this.loader;
	}

	private void setLoader(URLClassLoader loader) {
		this.loader = loader;
	}

	private Set<File> getClassPath() {
		return this.classPath;
	}

	public void setClassPath(Set<File> classesPath) {
		this.classPath = classesPath;
		this.reinitPrivateClassLoader();
	}

	private Set<File> getTestClassPath() {
		return this.testClassPath;
	}

	public void setTestClassPath(Set<File> testClassesPath) {
		this.testClassPath = testClassesPath;
		this.reinitPrivateClassLoader();
	}

	public HashSet<String> determineUnloadableTestClassNames(Set<String> fqTestClassNames) {

		HashSet<String> unloadableClasses = new HashSet<String>();

		for (String testClassName : fqTestClassNames) {

			try {

				Class<?> testClass = this.getLoader().loadClass(testClassName);

				if (Modifier.isAbstract(testClass.getModifiers()) || Modifier.isInterface(testClass.getModifiers())) {
					this.log.info("Skipping abstract class or interface in test set:" + testClassName);
					unloadableClasses.add(testClassName);
					continue;
				}

			} catch (Exception e) {
				this.log.warn("Skipping class in test set due to class loading problem:" + testClassName);
				this.log.debug(e);
				unloadableClasses.add(testClassName);
				continue;
			}

		}

		return unloadableClasses;
	}

}
