package com.mutation.resolver;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.mutation.annotations.TestClassToClassMapping;
import com.mutation.report.generator.utils.ClassNameAnalyzer;
import com.mutation.report.generator.utils.ClassNameAnalyzer.ClassNameInfo;
import com.mutation.runner.ClassDescription;

public class AnnotationResolver extends AbstractFilteredResolver {

//	public AnnotationResolver(FilterConfiguration filters, File classesPath, File testClassesPath)
//			throws MalformedURLException {
//		super(filters, classesPath, testClassesPath);
//	}

	public List<ClassDescription> resolve() {

		DirectoryBasedResolver directoryResolver = new DirectoryBasedResolver();
		directoryResolver.setClassesBaseDir(getTestClassesPath());

		List<ClassDescription> testClassDescriptions = directoryResolver.determineClassNames();

		Map<String, ClassDescription> classDescriptions = new HashMap<String, ClassDescription>();

		List<ClassDescription> invokableTestClasses = removeNonInstantiatableClasses(testClassDescriptions);

		List<ClassDescription> usefulTestClasses = removeExcludedClasses(invokableTestClasses);

		determineAnnotationMappings(classDescriptions, usefulTestClasses);

		return new ArrayList<ClassDescription>(classDescriptions.values());
	}

	private void determineAnnotationMappings(Map<String, ClassDescription> classDescriptions, List<ClassDescription> usefulTestClasses) {
		for (ClassDescription testClassDescription : usefulTestClasses) {

			try {
				Class testClass = getLoader().loadClass(testClassDescription.getQualifiedClassName());

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
	}

}
