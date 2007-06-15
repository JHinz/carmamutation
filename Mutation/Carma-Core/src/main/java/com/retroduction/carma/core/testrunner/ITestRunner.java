package com.retroduction.carma.core.testrunner;

import java.util.Set;

import com.retroduction.carma.core.api.transitions.events.IEventListener;
import com.retroduction.carma.core.runner.Mutant;

public interface ITestRunner {

	void execute(Mutant mutant, Set<String> testNames, IEventListener eventListener);
	
	Set<String> execute(Set<String> testNames);

}
