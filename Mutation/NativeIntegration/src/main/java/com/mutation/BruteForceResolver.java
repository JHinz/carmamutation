package com.mutation;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mutation.classesresolver.DirectoryBasedResolver;
import com.mutation.runner.ClassDescription;

public class BruteForceResolver implements IClassAndTestClassResolver {

	private File classesPath;

	private File testClassesPath;

	private String excludePattern;

	public BruteForceResolver() {
	}

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

	public String getExcludePattern() {
		return excludePattern;
	}

	public void setExcludePattern(String excludePattern) {
		this.excludePattern = excludePattern;
	}

	public List<ClassDescription> resolve() {

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(classesPath);

		List<ClassDescription> classDescriptions = directoryResolver.determineClassNames();

		directoryResolver.setClassesBaseDir(testClassesPath);

		List<ClassDescription> testClassDescriptions = directoryResolver.determineClassNames();
		
		if ( excludePattern == null) {
			excludePattern = "\\(.*\\)";
		}

		Pattern pattern = Pattern.compile(excludePattern);

		for (ClassDescription classDescription : classDescriptions) {

			classDescription.setAssociatedTestNames(new HashSet<String>());

			for (ClassDescription testClassDescription : testClassDescriptions) {

				String fqTestClassName = "";

				if (testClassDescription.getPackageName() != null
						&& !testClassDescription.getPackageName().trim().equals("")) {
					fqTestClassName = testClassDescription.getPackageName() + ".";
				}

				fqTestClassName += testClassDescription.getClassName();

				Matcher matcher = pattern.matcher(fqTestClassName);

				if (!matcher.find()) {
					classDescription.getAssociatedTestNames().add(fqTestClassName);
				}
			}
		}

		return classDescriptions;
	}

}
