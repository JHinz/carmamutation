package com.retroduction.carma.resolvers.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.core.om.PersistentClassInfo;

public class FilterVerifier {

	private Log log = LogFactory.getLog(FilterVerifier.class);

	private FilterConfiguration filterConfiguration;

	public Set<String> determineExcludedClassNames(Set<String> classNames) {

		Set<String> excludedClasses = new HashSet<String>();

		for (String classDescription : classNames) {

			if (classDescription == null || classDescription.trim().equals("")) {
				excludedClasses.add(classDescription);
				continue;
			}

			if (!this.getFilterConfiguration().getIncludeFilter().shouldBeIncluded(classDescription)) {
				excludedClasses.add(classDescription);
				this.log.debug(classDescription + "> should be excluded due to include filter");
				continue;
			}

			if (this.getFilterConfiguration().getExcludeFilter().shouldBeExcluded(classDescription)) {
				excludedClasses.add(classDescription);
				this.log.debug(classDescription + "> should be excluded due to exclude filter");
				continue;
			}

		}

		return excludedClasses;
	}

	public Set<PersistentClassInfo> determineExcludedClasses(Set<PersistentClassInfo> classes) {

		Set<String> classNames = new HashSet<String>();
		for (PersistentClassInfo clazz : classes)
			classNames.add(clazz.getFullyQualifiedClassName());

		Set<String> needlessClassNames = this.determineExcludedClassNames(classNames);

		Set<PersistentClassInfo> needlessClasses = new HashSet<PersistentClassInfo>();

		Iterator<PersistentClassInfo> classIterator = classes.iterator();

		while (classIterator.hasNext()) {
			PersistentClassInfo clazz = classIterator.next();

			if (needlessClassNames.contains(clazz.getFullyQualifiedClassName()))
				needlessClasses.add(clazz);
		}

		return needlessClasses;

	}

	private FilterConfiguration getFilterConfiguration() {
		return this.filterConfiguration;
	}

	public void setFilterConfiguration(FilterConfiguration filterConfiguration) {
		this.filterConfiguration = filterConfiguration;
	}
}
