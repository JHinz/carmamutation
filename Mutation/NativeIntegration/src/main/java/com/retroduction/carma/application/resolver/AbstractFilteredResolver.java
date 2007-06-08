package com.retroduction.carma.application.resolver;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.application.IResolver;
import com.retroduction.carma.application.resolver.util.FilterConfiguration;
import com.retroduction.carma.core.runner.ClassDescription;

public abstract class AbstractFilteredResolver implements IResolver {

	private Log log = LogFactory.getLog(AbstractFilteredResolver.class);
	
	private FilterConfiguration filterConfiguration;

	private File classesPath;

	private File testClassesPath;

	private URLClassLoader loader;

	private void reinitPrivateClassLoader() throws MalformedURLException {
		List<URL> urlList = new ArrayList<URL>();

		if (getClassesPath() != null)
			urlList.add(getClassesPath().toURL());

		if (getTestClassesPath() != null)
			urlList.add(getTestClassesPath().toURL());

		URL[] bla = (URL[]) urlList.toArray(new URL[0]);

		setLoader(new URLClassLoader(bla, this.getClass().getClassLoader()));
	}

	protected URLClassLoader getLoader() {
		return loader;
	}

	private void setLoader(URLClassLoader loader) {
		this.loader = loader;
	}

	public File getClassesPath() {
		return classesPath;
	}

	public void setClassesPath(File classesPath) throws MalformedURLException {
		this.classesPath = classesPath;
		reinitPrivateClassLoader();
	}

	public File getTestClassesPath() {
		return testClassesPath;
	}

	public void setTestClassesPath(File testClassesPath) throws MalformedURLException {
		this.testClassesPath = testClassesPath;
		reinitPrivateClassLoader();
	}

	protected List<ClassDescription> removeExcludedClasses(List<ClassDescription> classDescriptions) {

		List<ClassDescription> excludedClasses = new ArrayList<ClassDescription>();

		for (ClassDescription classDescription : classDescriptions) {

			if (!getFilterConfiguration().getClassIncludeFilter().shouldBeIncluded(
					classDescription.getQualifiedClassName())) {
				excludedClasses.add(classDescription);
				continue;
			}

			if (getFilterConfiguration().getClassExcludeFilter().shouldBeExcluded(
					classDescription.getQualifiedClassName())) {
				excludedClasses.add(classDescription);
				continue;
			}

		}

		for (ClassDescription classDescription : excludedClasses) {
			classDescriptions.remove(classDescription);
		}

		return classDescriptions;
	}

	protected List<ClassDescription> removeExcludedTestClasses(List<ClassDescription> classDescriptions) {

		List<ClassDescription> excludedClasses = new ArrayList<ClassDescription>();

		for (ClassDescription classDescription : classDescriptions) {

			if (!getFilterConfiguration().getTestClassIncludeFilter().shouldBeIncluded(
					classDescription.getQualifiedClassName())) {
				excludedClasses.add(classDescription);
				continue;
			}

			if (getFilterConfiguration().getTestClassExcludeFilter().shouldBeExcluded(
					classDescription.getQualifiedClassName())) {
				excludedClasses.add(classDescription);
				continue;
			}

		}

		for (ClassDescription classDescription : excludedClasses) {
			classDescriptions.remove(classDescription);
		}

		return classDescriptions;
	}

	protected List<ClassDescription> removeNonInstantiatableClasses(List<ClassDescription> classes) {

		List<ClassDescription> usableTestClassDescriptions = new ArrayList<ClassDescription>();

		for (ClassDescription testClassDescription : classes) {

			if (!getFilterConfiguration().getTestClassIncludeFilter().shouldBeIncluded(
					testClassDescription.getQualifiedClassName())) {
				continue;
			}

			if (getFilterConfiguration().getTestClassExcludeFilter().shouldBeExcluded(
					testClassDescription.getQualifiedClassName())) {
				log.info("Skipping class in test set due to exclude filter:"
						+ testClassDescription.getQualifiedClassName());
				continue;
			}

			try {
				Class testClass = loader.loadClass(testClassDescription.getQualifiedClassName());

				if (Modifier.isAbstract(testClass.getModifiers()) || Modifier.isInterface(testClass.getModifiers())) {
					log.info("Skipping abstract class or interface in test set:"
							+ testClassDescription.getQualifiedClassName());
					continue;
				}

			} catch (ClassNotFoundException e) {
				log.warn("Skipping class in test set due to class loading problem:"
						+ testClassDescription.getQualifiedClassName());
				continue;
			} catch (NoClassDefFoundError e) {
				log.warn("Skipping class in test set due to class loading problem:"
						+ testClassDescription.getQualifiedClassName());
				continue;
			}
			usableTestClassDescriptions.add(testClassDescription);
		}

		for (ClassDescription classDescription : usableTestClassDescriptions) {
			classes.remove(classDescription);
		}

		return usableTestClassDescriptions;
	}

	public FilterConfiguration getFilterConfiguration() {
		return filterConfiguration;
	}

	public void setFilterConfiguration(FilterConfiguration filterConfiguration) {
		this.filterConfiguration = filterConfiguration;
	}
}
