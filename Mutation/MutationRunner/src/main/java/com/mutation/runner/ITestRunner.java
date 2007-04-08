package com.mutation.runner;

import java.util.Set;

import com.mutation.runner.events.IEventListener;

public interface ITestRunner {
	
	void execute(Mutant mutant, Set<String> testNames, IEventListener eventListener);

}
