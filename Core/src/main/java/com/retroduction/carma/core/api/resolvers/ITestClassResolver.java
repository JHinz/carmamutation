package com.retroduction.carma.core.api.resolvers;

import java.util.HashMap;
import java.util.Set;

public interface ITestClassResolver {

	public HashMap<String, Set<String>> resolve(Set<String> classNames);

}
