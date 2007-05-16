package com.mutation.resolver;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import com.mutation.runner.ClassDescription;

public class BruteForceResolver extends AbstractFilteredResolver {

	private File classesPath;

	private File testClassesPath;

	public File getClassesPath() {
		return classesPath;
	}

	public void setClassesPath(File classesPath) {
		this.classesPath = classesPath;
	}

	public File getTestClassesPath() {
		return testClassesPath;
	}

	public void setTestClassesPath(File testClassesPath) {
		this.testClassesPath = testClassesPath;
	}

	public List<ClassDescription> resolve() {

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(classesPath);

		List<ClassDescription> classDescriptions = directoryResolver.determineClassNames();

		directoryResolver.setClassesBaseDir(testClassesPath);

		List<ClassDescription> testClassDescriptions = directoryResolver.determineClassNames();

		for (ClassDescription classDescription : classDescriptions) {

			classDescription.setAssociatedTestNames(new HashSet<String>());

			for (ClassDescription testClassDescription : testClassDescriptions) {

				String fqTestClassName = "";

				if (testClassDescription.getPackageName() != null
						&& !testClassDescription.getPackageName().trim().equals("")) {
					fqTestClassName = testClassDescription.getPackageName() + ".";
				}

				fqTestClassName += testClassDescription.getClassName();

				if (!getFilter().shouldBeExcluded(fqTestClassName))
					classDescription.getAssociatedTestNames().add(fqTestClassName);

			}
		}

		return classDescriptions;
	}

}
