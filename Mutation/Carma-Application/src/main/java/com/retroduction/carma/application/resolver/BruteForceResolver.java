package com.retroduction.carma.application.resolver;

import java.util.HashSet;
import java.util.List;

import com.retroduction.carma.core.api.testrunners.ClassDescription;

public class BruteForceResolver extends AbstractFilteredResolver {

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
