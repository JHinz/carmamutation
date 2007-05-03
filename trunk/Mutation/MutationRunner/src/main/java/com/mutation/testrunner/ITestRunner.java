package com.mutation.testrunner;

import java.util.Set;

import com.mutation.runner.Mutant;
import com.mutation.runner.events.IEventListener;

public interface ITestRunner {
	
	void execute(Mutant mutant, Set<String> testNames, IEventListener eventListener);

}
