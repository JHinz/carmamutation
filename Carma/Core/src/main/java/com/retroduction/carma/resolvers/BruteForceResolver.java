/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
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
