package com.mutation.resolver;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
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

		URLClassLoader loader = null;
		try {
			loader = new URLClassLoader(new URL[] { this.testClassesPath.toURL(), this.classesPath.toURL() }, this
					.getClass().getClassLoader());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return new ArrayList<ClassDescription>();
		}

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

				if (getFilter().shouldBeExcluded(fqTestClassName)) {
					System.out.println("Skipping class in test set due to exclude filter:" + fqTestClassName);
					continue;
				}

				try {
					Class testClass = loader.loadClass(fqTestClassName);

					if (Modifier.isAbstract(testClass.getModifiers()) || Modifier.isInterface(testClass.getModifiers())) {
						System.out.println("Skipping abstract class or interface in test set:" + fqTestClassName);
						continue;
					}

				} catch (ClassNotFoundException e) {
					System.out.println("Skipping class in test set due to class loading problem:" + fqTestClassName);
					continue;
				}

				classDescription.getAssociatedTestNames().add(fqTestClassName);
			}
		}

		return classDescriptions;
	}
}
