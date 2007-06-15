package com.retroduction.carma.resolvers.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.core.api.testrunners.IFilterVerifier;


public class FilterVerifier implements IFilterVerifier {

	private Log log = LogFactory.getLog(FilterVerifier.class);

	private FilterConfiguration filterConfiguration;

	public Set<String> removeExcludedClasses(Set<String> classDescriptions) {

		LinkedHashSet<String> removedClassNames = new LinkedHashSet<String>();

		for (String classDescription : classDescriptions) {

			if (!getFilterConfiguration().getIncludeFilter().shouldBeIncluded(classDescription)) {
				removedClassNames.add(classDescription);
				log.debug("Removing class <" + classDescription + "> due to negative match of include filter");
				continue;
			}

			if (getFilterConfiguration().getExcludeFilter().shouldBeExcluded(classDescription)) {
				removedClassNames.add(classDescription);
				log.debug("Removing class <" + classDescription + "> due to positive match of exclude filter");
				continue;
			}

		}

		for (String removedClassName : removedClassNames) {
			classDescriptions.remove(removedClassName);
		}

		return classDescriptions;
	}

	private FilterConfiguration getFilterConfiguration() {
		return filterConfiguration;
	}

	public void setFilterConfiguration(FilterConfiguration filterConfiguration) {
		this.filterConfiguration = filterConfiguration;
	}
}
