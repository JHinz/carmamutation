package com.retroduction.carma.resolvers;

import java.util.Set;

import com.retroduction.carma.core.om.PersistentClassInfo;

public interface IClassResolver {

	public Set<PersistentClassInfo> determineClassNames();

}
