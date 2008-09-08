/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.resolvers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.retroduction.carma.core.api.resolvers.ITestClassResolver;
import com.retroduction.carma.core.om.PersistentClassInfo;

public class BruteForceResolver implements ITestClassResolver {

	private File[] testClassesPath;

	public File[] getTestClassesPath() {
		return this.testClassesPath;
	}

	public void setTestClassesPath(File[] testClassesPath) throws MalformedURLException {
		this.testClassesPath = testClassesPath;
	}

	public HashMap<String, Set<String>> resolve(Set<String> classNames) {

		FileClassResolver directoryResolver = new FileClassResolver();

		directoryResolver.setClassesBaseDir(this.getTestClassesPath());

		Set<PersistentClassInfo> existingTestClasses = directoryResolver.determineClassNames();

		HashMap<String, Set<String>> result = new HashMap<String, Set<String>>();

		for (String clazz : classNames) {

			Set<String> testNamesForClass = result.get(clazz);

			if (testNamesForClass == null) {
				testNamesForClass = new HashSet<String>();
			}

			for (PersistentClassInfo testClassName : existingTestClasses) {
				testNamesForClass.add(testClassName.getFullyQualifiedClassName());
			}

			result.put(clazz, testNamesForClass);
		}
		return result;
	}
}
