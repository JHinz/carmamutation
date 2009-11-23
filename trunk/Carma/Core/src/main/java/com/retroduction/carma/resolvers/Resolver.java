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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.retroduction.carma.core.api.resolvers.IResolver;
import com.retroduction.carma.core.api.resolvers.ITestClassResolver;
import com.retroduction.carma.core.api.testrunners.ITestCaseInstantiationVerifier;
import com.retroduction.carma.core.om.PersistentClassInfo;
import com.retroduction.carma.core.om.TestedClassInfo;
import com.retroduction.carma.resolvers.util.FilterVerifier;
import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

/**
 * This resolver performs common tasks like applying class and test class
 * filters, checking whether test classes are usable (can be instantiated) and
 * creates a full object model with relations between classes and associated
 * tests. Furthermore this class adds file system information (class file
 * location, source file location,...) to the resulting objects.
 * 
 * 
 * @author arau
 * 
 */
public class Resolver implements IResolver {

	private Logger logger = LoggerFactory.getLogger(Resolver.class);

	private FilterVerifier classFilterVerifier;

	private FilterVerifier testClassFilterVerifier;

	private ITestCaseInstantiationVerifier instantiationVerifier;

	private ITestClassResolver testClassResolver;

	private IClassResolver classResolver;

	public void setClassFilterVerifier(FilterVerifier filterVerifier) {
		this.classFilterVerifier = filterVerifier;
	}

	public void setInstantiationVerifier(ITestCaseInstantiationVerifier instantiationVerifier) {
		this.instantiationVerifier = instantiationVerifier;
	}

	public void setTestClassResolver(ITestClassResolver testClassResolver) {
		this.testClassResolver = testClassResolver;
	}

	public Set<TestedClassInfo> resolve() {

		this.logger.debug("Resolving classes from configured classes directory/directories");

		Set<PersistentClassInfo> classDescriptions = this.classResolver.determineClassNames();

		Set<PersistentClassInfo> needlessClasses = this.classFilterVerifier.determineExcludedClasses(classDescriptions);

		classDescriptions.removeAll(needlessClasses);

		this.logger.info("Resolving test classes using resolver: " + this.testClassResolver.getClass().getName());

		Set<String> allClassNames = new HashSet<String>();
		for (PersistentClassInfo clazz : classDescriptions) {
			allClassNames.add(clazz.getFullyQualifiedClassName());
		}

		HashMap<String, Set<String>> classAndTestMap = this.testClassResolver.resolve(allClassNames);

		Set<TestedClassInfo> result = new HashSet<TestedClassInfo>();

		for (PersistentClassInfo clazz : classDescriptions) {

			Set<String> testNames = classAndTestMap.get(clazz.getFullyQualifiedClassName());

			Set<String> needlessTestClasses = this.testClassFilterVerifier.determineExcludedClassNames(testNames);

			testNames.removeAll(needlessTestClasses);

			Set<String> unloadableTestClasses = this.instantiationVerifier.determineUnloadableTestClassNames(testNames);

			testNames.removeAll(unloadableTestClasses);

			Set<PersistentClassInfo> associatedTests = new HashSet<PersistentClassInfo>();
			for (String testName : testNames) {
				associatedTests.add(new PersistentClassInfo(testName));
			}
			TestedClassInfo classInfo = new TestedClassInfo(clazz);
			classInfo.getAssociatedTestNames().addAll(associatedTests);
			result.add(classInfo);

		}

		return result;

	}

	public void setTestClassFilterVerifier(FilterVerifier testClassFilterVerifier) {
		this.testClassFilterVerifier = testClassFilterVerifier;
	}

	public void setClassResolver(IClassResolver classResolver) {
		this.classResolver = classResolver;
	}

}
