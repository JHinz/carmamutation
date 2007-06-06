package com.mutation.resolver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mutation.runner.ClassDescription;

public class ClassMatchResolver extends AbstractFilteredResolver {

	private String testNameSuffix = "Test";

	public List<ClassDescription> resolve() {

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(getClassesPath());

		List<ClassDescription> classDescriptions = directoryResolver.determineClassNames();

		OneTestPerClassResolver testResolver = new OneTestPerClassResolver();
		testResolver.setTestCaseSuffix(getTestNameSuffix());

		for (ClassDescription classDescription : classDescriptions) {

			Set<String> testNames = testResolver.determineTests(classDescription.getQualifiedClassName());

			classDescription.setAssociatedTestNames(new HashSet<String>());

			for (String testName : testNames) {

				if (!getFilterConfiguration().getTestClassExcludeFilter().shouldBeExcluded(testName)) {
					classDescription.getAssociatedTestNames().add(testName);
				}

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
