/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.core.api.transitions;

import java.util.List;

import com.retroduction.carma.core.api.testrunners.om.Mutant;

public interface ITransition {

	public List<Mutant> applyTransitions(byte[] byteCode);

	public String getName();

}
