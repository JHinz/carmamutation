package com.retroduction.carma.core.api.resolvers;

import java.util.Set;

import com.retroduction.carma.core.om.TestedClassInfo;

public interface IResolver {

	public Set<TestedClassInfo> resolve();

}
