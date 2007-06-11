package com.retroduction.carma.application.resolver;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.application.resolver.util.FilterConfiguration;
import com.retroduction.carma.core.runner.ClassDescription;

public class FilterVerifier {

	private Log log = LogFactory.getLog(FilterVerifier.class);

	private FilterConfiguration filterConfiguration;

	public List<ClassDescription> removeExcludedClasses(List<ClassDescription> classDescriptions) {

		List<ClassDescription> excludedClasses = new ArrayList<ClassDescription>();

		for (ClassDescription classDescription : classDescriptions) {

			if (!getFilterConfiguration().getClassIncludeFilter().shouldBeIncluded(
					classDescription.getQualifiedClassName())) {
				excludedClasses.add(classDescription);
				continue;
			}

			if (getFilterConfiguration().getClassExcludeFilter().shouldBeExcluded(
					classDescription.getQualifiedClassName())) {
				excludedClasses.add(classDescription);
				continue;
			}

		}

		for (ClassDescription classDescription : excludedClasses) {
			classDescriptions.remove(classDescription);
		}

		return classDescriptions;
	}

	public List<ClassDescription> removeExcludedTestClasses(List<ClassDescription> classDescriptions) {

		List<ClassDescription> excludedClasses = new ArrayList<ClassDescription>();

		for (ClassDescription classDescription : classDescriptions) {

			if (!getFilterConfiguration().getTestClassIncludeFilter().shouldBeIncluded(
					classDescription.getQualifiedClassName())) {
				excludedClasses.add(classDescription);
				continue;
			}

			if (getFilterConfiguration().getTestClassExcludeFilter().shouldBeExcluded(
					classDescription.getQualifiedClassName())) {
				excludedClasses.add(classDescription);
				continue;
			}

		}

		for (ClassDescription classDescription : excludedClasses) {
			classDescriptions.remove(classDescription);
		}

		return classDescriptions;
	}

	public FilterConfiguration getFilterConfiguration() {
		return filterConfiguration;
	}

	public void setFilterConfiguration(FilterConfiguration filterConfiguration) {
		this.filterConfiguration = filterConfiguration;
	}
}
