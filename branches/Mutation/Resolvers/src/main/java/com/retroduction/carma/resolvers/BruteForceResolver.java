package com.retroduction.carma.resolvers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import com.retroduction.carma.core.api.resolvers.IResolver;
import com.retroduction.carma.core.api.testrunners.om.ClassDescription;

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

	public Set<ClassDescription> resolve() {

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(getClassesPath());

		Set<ClassDescription> classDescriptions = directoryResolver.determineClassNames();

		directoryResolver.setClassesBaseDir(getTestClassesPath());

		Set<ClassDescription> existingTestClasses = directoryResolver.determineClassNames();

		assignAllClassesAllTestClasses(existingTestClasses, classDescriptions);

		return classDescriptions;
	}

	private void assignAllClassesAllTestClasses(Set<ClassDescription> usefulTestClasses,
			Set<ClassDescription> usefulClasses) {
		for (ClassDescription clazz : usefulClasses) {

			clazz.setAssociatedTestNames(new HashSet<String>());

			for (ClassDescription testClassDescription : usefulTestClasses) {
				clazz.getAssociatedTestNames().add(testClassDescription.getQualifiedClassName());
			}
		}
	}

}
