package com.retroduction.carma.resolvers;

import java.util.HashSet;
import java.util.Set;

import com.retroduction.carma.core.api.resolvers.INestedResolver;
import com.retroduction.carma.core.api.resolvers.IResolver;
import com.retroduction.carma.core.api.testrunners.ITestCaseInstantiationVerifier;
import com.retroduction.carma.core.api.testrunners.om.ClassDescription;
import com.retroduction.carma.resolvers.util.FilterVerifier;

public class Resolver implements IResolver {

	private FilterVerifier classFilterVerifier;

	private FilterVerifier testClassFilterVerifier;

	private ITestCaseInstantiationVerifier instantiationVerifier;

	private INestedResolver nestedResolver;

	public void setClassFilterVerifier(FilterVerifier filterVerifier) {
		this.classFilterVerifier = filterVerifier;
	}

	public void setInstantiationVerifier(ITestCaseInstantiationVerifier instantiationVerifier) {
		this.instantiationVerifier = instantiationVerifier;
	}

	public void setNestedResolver(INestedResolver nestedResolver) {
		this.nestedResolver = nestedResolver;
	}

	public Set<ClassDescription> resolve() {

		return nestedResolver.resolve();

	}

	public Set<ClassDescription> removeSuperfluousClassNames(Set<ClassDescription> classesUnderTest) {

		HashSet<String> resolvedClassNames = new HashSet<String>();

		for (ClassDescription classDescription : classesUnderTest) {
			resolvedClassNames.add(classDescription.getQualifiedClassName());
		}

		Set<String> remainingClassesNames = classFilterVerifier.removeExcludedClasses(resolvedClassNames);

		remainingClassesNames = instantiationVerifier.removeNonInstantiatableClasses(remainingClassesNames);

		Set<ClassDescription> remainingClassDescriptions = new HashSet<ClassDescription>();

		for (ClassDescription classDescription : classesUnderTest) {
			if (remainingClassesNames.contains(classDescription.getQualifiedClassName()))
				remainingClassDescriptions.add(classDescription);
		}
		return remainingClassDescriptions;
	}

	public Set<ClassDescription> removeSuperfluousTestClasses(Set<ClassDescription> remainingClassDescriptions) {

		for (ClassDescription classUnderTestDescription : remainingClassDescriptions) {

			Set<String> associatedTestNames = classUnderTestDescription.getAssociatedTestNames();

			Set<String> remainingTestNames = testClassFilterVerifier.removeExcludedClasses(associatedTestNames);

			remainingTestNames = instantiationVerifier.removeNonInstantiatableClasses(remainingTestNames);

			classUnderTestDescription.setAssociatedTestNames(remainingTestNames);

		}

		return remainingClassDescriptions;
	}

	public void setTestClassFilterVerifier(FilterVerifier testClassFilterVerifier) {
		this.testClassFilterVerifier = testClassFilterVerifier;
	}

}
