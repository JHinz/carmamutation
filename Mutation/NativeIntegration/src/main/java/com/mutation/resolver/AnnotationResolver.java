package com.mutation.resolver;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mutation.IClassAndTestClassResolver;
import com.mutation.annotations.TestClassToClassMapping;
import com.mutation.classesresolver.DirectoryBasedResolver;
import com.mutation.runner.ClassDescription;

public class AnnotationResolver implements IClassAndTestClassResolver {

	private File classesPath;

	private File testClassesPath;

	public AnnotationResolver() {
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

	public List<ClassDescription> resolve() {

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(testClassesPath);

		List<ClassDescription> testClassDescriptions = directoryResolver.determineClassNames();

		Map<String, ClassDescription> classDescriptions = new HashMap<String, ClassDescription>();

		for (ClassDescription testClassDescription : testClassDescriptions) {

			try {
				Class classUnderTestClass = Class.forName(testClassDescription.getQualifiedClassName());

				Annotation annotation = classUnderTestClass.getAnnotation(TestClassToClassMapping.class);

				String[] fqClassNames = ((TestClassToClassMapping) annotation).classNames();

				for (String fqClassName : fqClassNames) {

					ClassDescription classDescription = classDescriptions.get(fqClassName);

					if (classDescription == null) {
						classDescription = new ClassDescription();
					}

					splitFullyQualifiedJavaName(fqClassName);

				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private void splitFullyQualifiedJavaName(String fqClassName) {
		String packageName = "";
		String className = "";

		if (!fqClassName.contains(".")) {
			className = (fqClassName);
		} else {
			int lastDotIndex = fqClassName.lastIndexOf(".");
			packageName = fqClassName.substring(0, lastDotIndex - 1);
			className = fqClassName.substring(lastDotIndex + 1);
		}
	}

}
