package com.retroduction.carma.core.api.testrunners;

import java.util.Set;

public interface IFilterVerifier {
	
	public Set<String> removeExcludedClasses(Set<String> classDescriptions);
}
