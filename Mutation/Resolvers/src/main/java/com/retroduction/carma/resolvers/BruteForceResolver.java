package com.retroduction.carma.resolvers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;

import com.retroduction.carma.core.api.resolvers.IResolver;
import com.retroduction.carma.core.api.testrunners.ClassDescription;

public class BruteForceResolver implements IResolver {
	
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

	public List<ClassDescription> resolve() {

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(getClassesPath());

		List<ClassDescription> classDescriptions = directoryResolver.determineClassNames();

		directoryResolver.setClassesBaseDir(getTestClassesPath());

		List<ClassDescription> existingTestClasses = directoryResolver.determineClassNames();

		assignAllClassesAllTestClasses(existingTestClasses, classDescriptions);

		return classDescriptions;
	}

	private void assignAllClassesAllTestClasses(List<ClassDescription> usefulTestClasses,
			List<ClassDescription> usefulClasses) {
		for (ClassDescription clazz : usefulClasses) {

			clazz.setAssociatedTestNames(new HashSet<String>());

			for (ClassDescription testClassDescription : usefulTestClasses) {
				clazz.getAssociatedTestNames().add(testClassDescription.getQualifiedClassName());
			}
		}
	}

}
