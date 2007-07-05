package com.retroduction.carma.resolvers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import com.retroduction.carma.core.api.resolvers.INestedResolver;
import com.retroduction.carma.core.api.testrunners.om.ClassDescription;

public class ClassMatchResolver implements INestedResolver {

	private String testNameSuffix = "Test";

	private File[] classesPath;

	public void setClassesPath(File[] classesPath) throws MalformedURLException {
		this.classesPath = classesPath;
	}

	public Set<ClassDescription> resolve() {

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(classesPath);

		Set<ClassDescription> classDescriptions = directoryResolver.determineClassNames();

		assignTestNames(classDescriptions);

		return classDescriptions;
	}

	void assignTestNames(Set<ClassDescription> classDescriptions) {
		for (ClassDescription classDescription : classDescriptions) {

			String testName = classDescription.getQualifiedClassName() + getTestNameSuffix();

			classDescription.setAssociatedTestNames(new HashSet<String>());

			classDescription.getAssociatedTestNames().add(testName);

		}
	}

	public String getTestNameSuffix() {
		return testNameSuffix;
	}

	public void setTestNameSuffix(String testNameSuffix) {
		this.testNameSuffix = testNameSuffix;
	}

}
