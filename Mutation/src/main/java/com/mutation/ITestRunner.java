package com.mutation;

import java.util.Set;

import com.mutation.events.IEventListener;

public interface ITestRunner {
	
	void execute(Mutant mutant, Set<String> testNames, IEventListener eventListener);

}
