package com.retroduction.carma.testrunners.junit3;

import java.net.URL;

import com.retroduction.carma.core.api.testrunners.om.Mutant;

public interface IMutantJUnitRunner extends Runnable {

	// Non-Threaded API
	public int perform(String testCase, URL[] testClassesLocation, Mutant mutant);

	// Threaded API
	public void setMutant(Mutant mutant);

	public void setTestCase(String testCase);

	public void setTestClassesLocation(URL[] testClassesLocation);

	public int getErrorCount();
	
	public boolean finished();
	
	public void setFinishedSynchroLock(Object lock);
}
