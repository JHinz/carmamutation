package com.retroduction.carma.application.resolver;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mutation.annotations.TestClassToClassMapping;
import com.retroduction.carma.core.runner.ClassDescription;
import com.retroduction.carma.utilities.ClassNameAnalyzer;
import com.retroduction.carma.utilities.ClassNameAnalyzer.ClassNameInfo;

public class AnnotationResolver extends AbstractFilteredResolver {

	private Log log = LogFactory.getLog(AnnotationResolver.class);

	private URLClassLoader loader;

	private URLClassLoader getLoader() {
		return loader;
	}

	private void setLoader(URLClassLoader loader) {
		this.loader = loader;
	}

	@Override
	public void setClassesPath(File[] classesPath) throws MalformedURLException {
		super.setClassesPath(classesPath);
		reinitPrivateClassLoader();
	}

	@Override
	public void setTestClassesPath(File[] testClassesPath) throws MalformedURLException {
		super.setTestClassesPath(testClassesPath);
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

	public List<ClassDescription> resolve() {

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(getTestClassesPath());

		List<ClassDescription> testClassDescriptions = directoryResolver.determineClassNames();

		List<ClassDescription> annotationBasedClassDescriptions = determineAnnotationMappings(testClassDescriptions);

		return annotationBasedClassDescriptions;
	}

	private List<ClassDescription> determineAnnotationMappings(List<ClassDescription> testClassDescriptions) {

		HashMap<String, ClassDescription> resolvedClasses = new HashMap<String, ClassDescription>();

		for (ClassDescription testClassDescription : testClassDescriptions) {

			try {
				Class testClass = getLoader().loadClass(testClassDescription.getQualifiedClassName());

				Annotation annotation = testClass.getAnnotation(TestClassToClassMapping.class);

				if (annotation == null)
					continue;

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
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return new ArrayList<ClassDescription>(resolvedClasses.values());
	}

}
