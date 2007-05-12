package com.mutation.testrunner;

import java.util.List;

import com.mutation.runner.Mutant;
import com.mutation.runner.events.IEventListener;

public interface ITestRunner {

	void execute(Mutant mutant, List<String> testNames, IEventListener eventListener);

}
