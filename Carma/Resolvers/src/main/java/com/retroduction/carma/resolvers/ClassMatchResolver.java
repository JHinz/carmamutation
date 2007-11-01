package com.retroduction.carma.resolvers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.retroduction.carma.core.api.resolvers.ITestClassResolver;
import com.retroduction.carma.utilities.ClassInfo;

public class ClassMatchResolver implements ITestClassResolver {

	private String testNameSuffix = "Test";
	private String testNamePrefix = "";

	public HashMap<String, Set<String>> resolve(Set<String> classNames) {

		HashMap<String, Set<String>> result = new HashMap<String, Set<String>>();

		for (String clazz : classNames) {

			ClassInfo info = new ClassInfo(clazz);
			String packagePrefix = info.getPackageName();
			if(0 != info.getPackageName().length()){
				packagePrefix += ".";
			}
			String testName = packagePrefix  +this.testNamePrefix +info.getClassName() + this.testNameSuffix;

			HashSet<String> tests = new HashSet<String>();

			tests.add(testName);

			result.put(clazz, tests);

		}

		return result;
	}

	public void setTestNameSuffix(String testNameSuffix) {
		this.testNameSuffix = testNameSuffix;
	}

	public void setTestNamePrefix(String testNamePrefix) {
		this.testNamePrefix = testNamePrefix;
	}

}
