package com.retroduction.carma.core.api.testrunners;

import java.util.HashSet;
import java.util.Set;

public interface ITestCaseInstantiationVerifier {
	
	public HashSet<String> removeNonInstantiatableClasses(Set<String> fqClassNames) ;

}
