package com.retroduction.carma.resolvers;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.annotations.TestClassToClassMapping;
import com.retroduction.carma.core.api.resolvers.INestedResolver;
import com.retroduction.carma.core.api.testrunners.om.ClassDescription;
import com.retroduction.carma.utilities.ClassNameAnalyzer;
import com.retroduction.carma.utilities.ClassNameAnalyzer.ClassNameInfo;

public class AnnotationResolver implements INestedResolver {

	private Log log = LogFactory.getLog(AnnotationResolver.class);

	private URLClassLoader loader;

	private File[] classesPath;

	private File[] testClassesPath;

	public File[] getClassesPath() {
		return classesPath;
	}

	public File[] getTestClassesPath() {
		return testClassesPath;
	}

	private URLClassLoader getLoader() {
		return loader;
	}

	private void setLoader(URLClassLoader loader) {
		this.loader = loader;
	}

	public void setClassesPath(File[] classesPath) throws MalformedURLException {
		this.classesPath = classesPath;
		reinitPrivateClassLoader();
	}

	public void setTestClassesPath(File[] testClassesPath) throws MalformedURLException {
		this.testClassesPath = testClassesPath;
		reinitPrivateClassLoader();
	}

	private void reinitPrivateClassLoader() {

		List<URL> urlList = new ArrayList<URL>();

		if (getClassesPath() != null) {
			for (File file : getClassesPath()) {
				if (file != null) {
					try {
						urlList.add(file.toURL());
					} catch (MalformedURLException e) {
						log.warn("Invalid class path entry: " + file.toString());
					}
				}
			}
		}

		if (getTestClassesPath() != null) {
			for (File file : getTestClassesPath()) {
				if (file != null) {
					try {
						urlList.add(file.toURL());
					} catch (MalformedURLException e) {
						log.warn("Invalid class path entry: " + file.toString());
					}
				}
			}
		}

		setLoader(new URLClassLoader((URL[]) urlList.toArray(new URL[0]), this.getClass().getClassLoader()));
	}

	public Set<ClassDescription> resolve() {

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(getTestClassesPath());

		Set<ClassDescription> testClassDescriptions = directoryResolver.determineClassNames();

		Set<ClassDescription> annotationBasedClassDescriptions = determineAnnotationMappings(testClassDescriptions);

		return annotationBasedClassDescriptions;
	}

	private Set<ClassDescription> determineAnnotationMappings(Set<ClassDescription> testClassDescriptions) {

		HashMap<String, ClassDescription> resolvedClasses = new HashMap<String, ClassDescription>();

		for (ClassDescription testClassDescription : testClassDescriptions) {

		
				Class testClass = null;
				
				try {
					testClass = getLoader().loadClass(testClassDescription.getQualifiedClassName());
				} catch (Throwable e) {
					log.warn("TestClass could not be loaded: " + testClassDescription.getQualifiedClassName());
					continue;
				}

				Annotation annotation = testClass.getAnnotation(TestClassToClassMapping.class);

				if (annotation == null) {
					log.warn("TestClass has no annotation: " + testClass.getCanonicalName());
					continue;
				}

				String[] fqClassNames = ((TestClassToClassMapping) annotation).classNames();

				for (String fqClassName : fqClassNames) {

					ClassDescription classDescription = resolvedClasses.get(fqClassName);

					if (classDescription == null) {
						ClassNameAnalyzer analyzer = new ClassNameAnalyzer();
						ClassNameInfo info = analyzer.extractClassNameInfo(fqClassName);

						classDescription = new ClassDescription();
						classDescription.setClassName(info.getClassName());
						classDescription.setPackageName(info.getPackageName());
						classDescription.setAssociatedTestNames(new HashSet<String>());

					}

					classDescription.getAssociatedTestNames().add(testClassDescription.getQualifiedClassName());

					resolvedClasses.put(classDescription.getQualifiedClassName(), classDescription);

				}
		}

		return new HashSet<ClassDescription>(resolvedClasses.values());
	}

}
