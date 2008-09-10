/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.core.api.testrunners;

import java.util.HashSet;
import java.util.Set;

public interface ITestCaseInstantiationVerifier {

	public HashSet<String> determineUnloadableTestClassNames(Set<String> fqClassNames);

}