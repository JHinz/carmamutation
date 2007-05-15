package com.mutation;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mutation.classesresolver.DirectoryBasedResolver;
import com.mutation.runner.ClassDescription;
import com.mutation.testsetresolver.OneTestPerClassResolver;

public class LegacyResolver implements IClassAndTestClassResolver {

	private String testNameSuffix = "Test";
	private File classesUnderTestPath;

	public LegacyResolver() {
		super();
	}

	public File getClassesUnderTestPath() {
		return classesUnderTestPath;
	}

	public void setClassesUnderTestPath(File classesUnderTestPath) {
		this.classesUnderTestPath = classesUnderTestPath;
	}

	public List<ClassDescription> resolve() {

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(classesUnderTestPath);

		List<ClassDescription> classDescriptions = directoryResolver.determineClassNames();

		OneTestPerClassResolver testResolver = new OneTestPerClassResolver();
		testResolver.setTestCaseSuffix(getTestNameSuffix());

		for (ClassDescription classDescription : classDescriptions) {

			//TODO seems to be useless  - is overwritten 2 lines later anyway. remove that line?
			classDescription.setAssociatedTestNames(new HashSet<String>());

			Set<String> testNames = testResolver.determineTests( classDescription.getQualifiedClassName());

			classDescription.setAssociatedTestNames(testNames);
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
