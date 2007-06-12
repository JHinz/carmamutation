package com.retroduction.carma.application.resolver;

import java.io.File;
import java.net.MalformedURLException;

import com.retroduction.carma.application.IResolver;

public abstract class AbstractFilteredResolver implements IResolver {

	private File[] classesPath;

	private File[] testClassesPath;

	public File[] getClassesPath() {
		return classesPath;
	}

	public void setClassesPath(File[] classesPath) throws MalformedURLException {
		this.classesPath = classesPath;
	}

	public File[] getTestClassesPath() {
		return testClassesPath;
	}

	public void setTestClassesPath(File[] testClassesPath) throws MalformedURLException {
		this.testClassesPath = testClassesPath;
	}

}
