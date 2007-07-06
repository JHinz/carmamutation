package com.retroduction.carma.core.api.resolvers;

import java.util.Set;

import com.retroduction.carma.core.api.testrunners.om.ClassDescription;

public interface IResolver {

	public Set<ClassDescription> resolve();

	public Set<ClassDescription> removeSuperfluousClassNames(Set<ClassDescription> classesUnderTest);

	public Set<ClassDescription> removeSuperfluousTestClasses(Set<ClassDescription> remainingClassDescriptions);
}
