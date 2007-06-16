package com.retroduction.carma.resolvers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.retroduction.carma.core.api.eventlisteners.IEventListener;
import com.retroduction.carma.core.api.eventlisteners.om.ClassesUnderTestResolved;
import com.retroduction.carma.core.api.eventlisteners.om.TestSetDetermined;
import com.retroduction.carma.core.api.resolvers.IResolver;
import com.retroduction.carma.core.api.testrunners.om.ClassDescription;
import com.retroduction.carma.resolvers.util.FilterVerifier;
import com.retroduction.carma.resolvers.util.TestCaseInstantiationVerifier;

public class Resolver implements IResolver {

	private FilterVerifier filterVerifier;

	private TestCaseInstantiationVerifier instantiationVerifier;

	private IResolver nestedResolver;

	private File[] classesPath;

	private File[] testClassesPath;

	private IEventListener eventListener;

	public void setEventListener(IEventListener eventListener) {
		this.eventListener = eventListener;
	}

	public File[] getClassesPath() {
		return classesPath;
	}

	public void setClassesPath(File[] classesPath) throws MalformedURLException {
		this.classesPath = classesPath;
	}

	public File[] getTestClassesPath() {
		return testClassesPath;
	}

	public void setTestClassesPath(File[] testClassesPath) throws MalformedURLException {
		this.testClassesPath = testClassesPath;
	}

	public void setFilterVerifier(FilterVerifier filterVerifier) {
		this.filterVerifier = filterVerifier;
	}

	public void setInstantiationVerifier(TestCaseInstantiationVerifier instantiationVerifier) {
		this.instantiationVerifier = instantiationVerifier;
	}

	public void setNestedResolver(IResolver nestedResolver) {
		this.nestedResolver = nestedResolver;
	}

	public Set<ClassDescription> resolve() {

		// TODO: remove uneccessary conversions between list -> set -> list !
		Set<ClassDescription> classDescriptions = new HashSet<ClassDescription>(nestedResolver.resolve());

		Set<ClassDescription> remainingClassUnderTest = removeSuperfluousClassNames(classDescriptions);

		eventListener
				.notifyEvent(new ClassesUnderTestResolved(new ArrayList<ClassDescription>(remainingClassUnderTest)));

		Set<ClassDescription> remainingClassesUnderTestWithWorkingTestClasses = removeSuperfluousTestClasses(remainingClassUnderTest);

		return remainingClassesUnderTestWithWorkingTestClasses;

	}

	Set<ClassDescription> removeSuperfluousClassNames(Set<ClassDescription> classesUnderTest) {

		HashSet<String> resolvedClassNames = new HashSet<String>();

		for (ClassDescription classDescription : classesUnderTest) {
			resolvedClassNames.add(classDescription.getQualifiedClassName());
		}

		Set<String> remainingClassesNames = filterVerifier.removeExcludedClasses(resolvedClassNames);

		remainingClassesNames = instantiationVerifier.removeNonInstantiatableClasses(remainingClassesNames);

		Set<ClassDescription> remainingClassDescriptions = new HashSet<ClassDescription>();

		for (ClassDescription classDescription : classesUnderTest) {
			if (remainingClassesNames.contains(classDescription.getQualifiedClassName()))
				remainingClassDescriptions.add(classDescription);
		}
		return remainingClassDescriptions;
	}

	private Set<ClassDescription> removeSuperfluousTestClasses(Set<ClassDescription> remainingClassDescriptions) {

		for (ClassDescription classUnderTestDescription : remainingClassDescriptions) {

			Set<String> associatedTestNames = classUnderTestDescription.getAssociatedTestNames();

			Set<String> remainingTestNames = filterVerifier.removeExcludedClasses(associatedTestNames);

			remainingTestNames = instantiationVerifier.removeNonInstantiatableClasses(remainingTestNames);

			classUnderTestDescription.setAssociatedTestNames(remainingTestNames);

			eventListener.notifyEvent(new TestSetDetermined(classUnderTestDescription.getQualifiedClassName(),
					remainingTestNames));

		}

		return remainingClassDescriptions;
	}


}
