/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.core.api.testrunners;

import java.util.Set;

import com.retroduction.carma.core.api.testrunners.om.Mutant;

public interface ITestRunner {

	void execute(Mutant mutant, Set<String> testNames);
	
	Set<String> execute(Set<String> testNames);

}
