package com.retroduction.carma.resolvers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import com.retroduction.carma.core.api.resolvers.IResolver;
import com.retroduction.carma.core.api.testrunners.om.ClassDescription;

public class ClassMatchResolver implements IResolver {

	private String testNameSuffix = "Test";

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

		OneTestPerClassResolver testResolver = new OneTestPerClassResolver();
		testResolver.setTestCaseSuffix(getTestNameSuffix());

		for (ClassDescription classDescription : classDescriptions) {

			Set<String> testNames = testResolver.determineTests(classDescription.getQualifiedClassName());

			classDescription.setAssociatedTestNames(new HashSet<String>());

			for (String testName : testNames) {
				classDescription.getAssociatedTestNames().add(testName);
			}

		}

		return classDescriptions;
	}

	public String getTestNameSuffix() {
		return testNameSuffix;
	}

	public void setTestNameSuffix(String testNameSuffix) {
		this.testNameSuffix = testNameSuffix;
	}

}
