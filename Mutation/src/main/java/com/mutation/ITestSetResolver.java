package com.mutation;

import java.util.Set;

import com.mutation.events.IEventListener;

public interface ITestSetResolver {

	Set<String> determineTests(String classUnderTestName, IEventListener eventListener);
	
}
