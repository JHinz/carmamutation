package com.retroduction.carma.application.resolver;

import java.util.HashSet;
import java.util.List;

import com.retroduction.carma.core.runner.ClassDescription;

public class BruteForceResolver extends AbstractFilteredResolver {

//	public BruteForceResolver(FilterConfiguration filters, File classesPath, File testClassesPath)
//			throws MalformedURLException {
//		super(filters, classesPath, testClassesPath);
//	}

	public List<ClassDescription> resolve() {

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(getClassesPath());

		List<ClassDescription> classDescriptions = directoryResolver.determineClassNames();

		directoryResolver.setClassesBaseDir(getTestClassesPath());

		List<ClassDescription> existingTestClasses = directoryResolver.determineClassNames();

		List<ClassDescription> invokableTestClasses = removeNonInstantiatableClasses(existingTestClasses);
		List<ClassDescription> usefulTestClasses = removeExcludedClasses(invokableTestClasses);

		List<ClassDescription> usefulClasses = removeExcludedClasses(classDescriptions);

		assignAllClassesAllTestClasses(usefulTestClasses, usefulClasses);

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
