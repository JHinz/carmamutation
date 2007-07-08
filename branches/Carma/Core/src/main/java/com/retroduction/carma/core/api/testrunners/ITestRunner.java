package com.retroduction.carma.core.api.testrunners;

import java.util.Set;

import com.retroduction.carma.core.api.testrunners.om.Mutant;

public interface ITestRunner {

	void execute(Mutant mutant, Set<String> testNames);
	
	Set<String> execute(Set<String> testNames);

}
