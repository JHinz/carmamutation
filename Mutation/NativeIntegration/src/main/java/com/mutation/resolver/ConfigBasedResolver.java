package com.mutation.resolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringTokenizer;

import com.mutation.annotations.TestClassToClassMapping;
import com.mutation.report.generator.utils.ClassNameAnalyzer;
import com.mutation.report.generator.utils.ClassNameAnalyzer.ClassNameInfo;
import com.mutation.runner.ClassDescription;

public class ConfigBasedResolver extends AbstractFilteredResolver {

	private File testClassesPath;

	private File classesPath;

	private File configurationFile;

	public File getConfigurationFile() {
		return configurationFile;
	}

	public void setConfigurationFile(File configurationFile) {
		this.configurationFile = configurationFile;
	}

	public File getTestClassesPath() {
		return testClassesPath;
	}

	public void setTestClassesPath(File testClassesPath) throws MalformedURLException {
		this.testClassesPath = testClassesPath;

	}

	protected String readInputConfiguration(InputStream steram) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(steram));

		StringBuffer resultBuffer = new StringBuffer();

		String line;
		while ((line = reader.readLine()) != null) {
			resultBuffer.append(line + "\n");
		}

		return resultBuffer.toString();
	}

	public List<ClassDescription> parseInputConfiguration(String inputConfig) {

		List<ClassDescription> result = new ArrayList<ClassDescription>();

		StringTokenizer lineTokenzier = new StringTokenizer(inputConfig, "\n");

		while (lineTokenzier.hasMoreTokens()) {

			String line = lineTokenzier.nextToken().replaceAll(" ", "");

			if (!line.contains("="))
				continue;

			int splitIndex = line.indexOf("=");

			if (splitIndex <= 0)
				continue;

			ClassDescription clazz = new ClassDescription();
			clazz.setClassName(line.substring(0, splitIndex));

			line = line.substring(splitIndex + 1, line.length());

			splitIndex = line.indexOf("=");

			if (splitIndex > 0)
				continue;

			StringTokenizer elementTokenizer = new StringTokenizer(line, ",");

			clazz.setAssociatedTestNames(new HashSet<String>());

			while (elementTokenizer.hasMoreTokens()) {
				clazz.getAssociatedTestNames().add(elementTokenizer.nextToken());

			}

			result.add(clazz);

		}

		return result;

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

		String inputConfiguration;
		try {
			FileInputStream reader = new FileInputStream(getConfigurationFile());
			inputConfiguration = readInputConfiguration(reader);
		} catch (IOException e1) {
			e1.printStackTrace();
			return new ArrayList<ClassDescription>(); 
		}

		List<ClassDescription> testClassDescriptions = parseInputConfiguration(inputConfiguration);

		Map<String, ClassDescription> classDescriptions = new HashMap<String, ClassDescription>();

		for (ClassDescription testClassDescription : testClassDescriptions) {

			if (getTestClassExcludeFilter().shouldBeExcluded(testClassDescription.getQualifiedClassName()))
				continue;

			try {
				Class testClass = loader.loadClass(testClassDescription.getQualifiedClassName());

				if (Modifier.isAbstract(testClass.getModifiers()) || Modifier.isInterface(testClass.getModifiers())) {
					System.out.println("Skipping abstract class or interface in test set:"
							+ testClassDescription.getQualifiedClassName());
					continue;
				}

			} catch (ClassNotFoundException e) {
				System.out.println("Skipping class in test set due to class loading problem:"
						+ testClassDescription.getQualifiedClassName());
				continue;
			}

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
