/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.testrunners.junit3;

import java.net.URL;

import com.retroduction.carma.core.api.testrunners.om.Mutant;

/**
 *
 * Threaded runner for testcases. Must not throw Exceptions ! Instead signal failure by setting
 * the error count appropriately !
 * 
 * @author arau
 *
 */
public interface IMutantJUnitRunner extends Runnable {

	public void setMutant(Mutant mutant);

	public void setTestCase(String testCase);

	public void setTestClassesLocation(URL[] testClassesLocation);

	public int getErrorCount();
	
	public boolean finished();
	
	public void setFinishedSynchroLock(Object lock);
}
