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

import com.retroduction.carma.annotations.TestClassToClassMapping;
import com.retroduction.carma.core.api.resolvers.ITestClassResolver;
import com.retroduction.carma.core.om.PersistentClassInfo;
import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

public class AnnotationResolver implements ITestClassResolver {

	private Logger log = LoggerFactory.getLogger(AnnotationResolver.class);

	private URLClassLoader loader;

	private File[] testClassesPath;

	public File[] getTestClassesPath() {
		return this.testClassesPath;
	}

	private URLClassLoader getLoader() {
		return this.loader;
	}

	private void setLoader(URLClassLoader loader) {
		this.loader = loader;
	}

	public void setTestClassesPath(File[] testClassesPath) throws MalformedURLException {
		this.testClassesPath = testClassesPath;
		this.reinitPrivateClassLoader();
	}

	private void reinitPrivateClassLoader() {

		List<URL> urlList = new ArrayList<URL>();

		if (this.getTestClassesPath() != null) {
			for (File file : this.getTestClassesPath()) {
				if (file != null) {
					try {
						urlList.add(file.toURL());
					} catch (MalformedURLException e) {
						this.log.warn("Invalid class path entry: " + file.toString());
					}
				}
			}
		}

		this.setLoader(new URLClassLoader(urlList.toArray(new URL[0]), this.getClass().getClassLoader()));
	}

	public HashMap<String, Set<String>> resolve(Set<String> classNames) {

		HashMap<String, Set<String>> result = new HashMap<String, Set<String>>();

		for (String className : classNames) {
			result.put(className, new HashSet<String>());
		}

		FileClassResolver directoryResolver = new FileClassResolver();
		directoryResolver.setClassesBaseDir(this.getTestClassesPath());

		Set<PersistentClassInfo> testClassNames = directoryResolver.determineClassNames();

		for (PersistentClassInfo testClassName : testClassNames) {

			Class<?> testClass = null;

			try {
				testClass = this.getLoader().loadClass(testClassName.getFullyQualifiedClassName());
			} catch (Throwable e) {
				this.log.warn("TestClass could not be loaded: " + testClassName);
				continue;
			}

			Annotation annotation = testClass.getAnnotation(TestClassToClassMapping.class);

			if (annotation == null) {
				this.log.warn("TestClass has no annotation: " + testClassName);
				continue;
			}

			String[] fqClassNames = ((TestClassToClassMapping) annotation).classNames();

			for (String fqClassName : fqClassNames) {

				if (result.containsKey(fqClassName)) {
					result.get(fqClassName).add(testClassName.getFullyQualifiedClassName());
				}

			}
		}
		return result;
	}
}
