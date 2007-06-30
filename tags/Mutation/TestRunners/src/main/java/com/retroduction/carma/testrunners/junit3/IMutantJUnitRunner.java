package com.retroduction.carma.testrunners.junit3;

import java.net.URL;

import com.retroduction.carma.core.api.testrunners.om.Mutant;

public interface IMutantJUnitRunner {

	public int perform(String testCase, URL[] testClassesLocation, Mutant mutant);

}
