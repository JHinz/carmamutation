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

import com.retroduction.carma.core.runner.ClassDescription;

public class InstantiationVerifier {

	private Log log = LogFactory.getLog(InstantiationVerifier.class);

	private File[] classesPath;

	private File[] testClassesPath;

	private URLClassLoader loader;

	private void reinitPrivateClassLoader() {

		List<URL> urlList = new ArrayList<URL>();

		if (getClassesPath() != null) {
			for (File file : getClassesPath()) {
				if (file != null) {
					try {
						urlList.add(file.toURL());
					} catch (MalformedURLException e) {
						log.warn("Invalid class path entry: " + file.toString());
					}
				}
			}
		}

		if (getTestClassesPath() != null) {
			for (File file : getTestClassesPath()) {
				if (file != null) {
					try {
						urlList.add(file.toURL());
					} catch (MalformedURLException e) {
						log.warn("Invalid class path entry: " + file.toString());
					}
				}
			}
		}

		setLoader(new URLClassLoader((URL[]) urlList.toArray(new URL[0]), this.getClass().getClassLoader()));
	}

	protected URLClassLoader getLoader() {
		return loader;
	}

	private void setLoader(URLClassLoader loader) {
		this.loader = loader;
	}

	public File[] getClassesPath() {
		return classesPath;
	}

	public void setClassesPath(File[] classesPath) {
		this.classesPath = classesPath;
		reinitPrivateClassLoader();
	}

	public File[] getTestClassesPath() {
		return testClassesPath;
	}

	public void setTestClassesPath(File[] testClassesPath) {
		this.testClassesPath = testClassesPath;
		reinitPrivateClassLoader();
	}

	public List<ClassDescription> removeNonInstantiatableClasses(List<ClassDescription> classes) {

		List<ClassDescription> usableTestClassDescriptions = new ArrayList<ClassDescription>();

		for (ClassDescription testClassDescription : classes) {

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

}
