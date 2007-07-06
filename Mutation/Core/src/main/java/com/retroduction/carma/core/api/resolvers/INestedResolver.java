package com.retroduction.carma.core.api.resolvers;

import java.util.Set;

import com.retroduction.carma.core.api.testrunners.om.ClassDescription;

public interface INestedResolver {

	public Set<ClassDescription> resolve();

}
