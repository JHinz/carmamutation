package com.mutation;

import java.util.List;

public interface ITestSetResolver {

	List<String> determineTests(String classUnderTestName);

}
