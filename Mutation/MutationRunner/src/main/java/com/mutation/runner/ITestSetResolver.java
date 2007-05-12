package com.mutation.runner;

import java.util.List;

public interface ITestSetResolver {

	List<String> determineTests(String classUnderTestName);

}
