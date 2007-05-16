package com.mutation.resolver;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.mutation.IResolver;
import com.mutation.annotations.TestClassToClassMapping;
import com.mutation.report.generator.utils.ClassNameAnalyzer;
import com.mutation.report.generator.utils.ClassNameAnalyzer.ClassNameInfo;
import com.mutation.runner.ClassDescription;

public class AnnotationResolver implements IResolver {

	private File testClassesPath;

	private File classesPath;

	private AnnotationClassLoader loader;

	private class AnnotationClassLoader extends URLClassLoader {

		public AnnotationClassLoader(URL[] urls, ClassLoader parent) {
			super(urls, parent);
		}

	}

	public AnnotationResolver() {
	}

	public File getTestClassesPath() {
		return testClassesPath;
	}

	public void setTestClassesPath(File testClassesPath) throws MalformedURLException {
		this.testClassesPath = testClassesPath;

	}

	public List<ClassDescription> resolve() {

		try {
			loader = new AnnotationClassLoader(new URL[] { this.testClassesPath.toURL(), this.classesPath.toURL() }, this
					.getClass().getClassLoader());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return new ArrayList<ClassDescription>();
		}

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(testClassesPath);

		List<ClassDescription> testClassDescriptions = directoryResolver.determineClassNames();

		Map<String, ClassDescription> classDescriptions = new HashMap<String, ClassDescription>();

		for (ClassDescription testClassDescription : testClassDescriptions) {

			try {
				Class testClass = loader.loadClass(testClassDescription.getQualifiedClassName());

				Annotation annotation = testClass.getAnnotation(TestClassToClassMapping.class);

				if (annotation == null)
					continue;

				String[] fqClassNames = ((TestClassToClassMapping) annotation).classNames();

				for (String fqClassName : fqClassNames) {

					ClassDescription classDescription = classDescriptions.get(fqClassName);

					if (classDescription == null) {
						ClassNameAnalyzer analyzer = new ClassNameAnalyzer();
						ClassNameInfo info = analyzer.extractClassNameInfo(fqClassName);

						classDescription = new ClassDescription();
						classDescription.setClassName(info.getClassName());
						classDescription.setPackageName(info.getPackageName());
						classDescription.setAssociatedTestNames(new HashSet<String>());

					}

					classDescription.getAssociatedTestNames().add(testClassDescription.getQualifiedClassName());

					classDescriptions.put(classDescription.getQualifiedClassName(), classDescription);

				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return new ArrayList<ClassDescription>(classDescriptions.values());
	}

	public File getClassesPath() {
		return classesPath;
	}

	public void setClassesPath(File classesPath) {
		this.classesPath = classesPath;
	}

}
