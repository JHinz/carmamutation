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

	private Set<URL> dependencyClassPath;

	private URLClassLoader loader;

	private void reinitPrivateClassLoader() {

		Set<File> combinedClassPathSet = new HashSet<File>();

		if (this.getClassPath() != null) {
			combinedClassPathSet.addAll(this.getClassPath());
		}

		if (this.getTestClassPath() != null) {
			combinedClassPathSet.addAll(this.getTestClassPath());
		}

		if (this.getDependencyClassPath() != null) {

			for (URL url : this.getDependencyClassPath()) {
				File file = new File(url.getFile());
				combinedClassPathSet.add(file);
			}

		}

		Set<URL> validURLs = this.filterInvalidURLs(combinedClassPathSet);

		this.setLoader(new URLClassLoader(validURLs.toArray(new URL[0]), this
				.getClass().getClassLoader()));
	}

	Set<URL> filterInvalidURLs(Set<File> classPathEntries) {

		Set<URL> result = new HashSet<URL>();

		if (classPathEntries != null) {
			for (File file : classPathEntries) {
				if (file != null) {
					try {
						result.add(file.toURL());
					} catch (MalformedURLException e) {
						this.log.warn("Invalid class path entry: "
								+ file.toString());
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

	public void setDependencyClassPath(Set<URL> dependencyClassPath) {
		this.dependencyClassPath = dependencyClassPath;
		this.reinitPrivateClassLoader();
	}

	private Set<URL> getDependencyClassPath() {
		return dependencyClassPath;
	}

	public HashSet<String> determineUnloadableTestClassNames(
			Set<String> fqTestClassNames) {

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
				this.log
						.warn("Skipping class in test set due to class loading problem:"
								+ testClassName);
				this.log.debug(e);
				unloadableClasses.add(testClassName);
				continue;
			} catch (NoClassDefFoundError e) {
				this.log
						.warn("Skipping class in test set due to class loading problem:"
								+ testClassName);
				this.log.debug(e);
				unloadableClasses.add(testClassName);
				continue;
			}

		}

		return unloadableClasses;
	}

}
