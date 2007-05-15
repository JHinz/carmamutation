package com.mutation;

import java.util.Set;

public interface ITestSetResolver {

	Set<String> determineTests(String classUnderTestName);

}
