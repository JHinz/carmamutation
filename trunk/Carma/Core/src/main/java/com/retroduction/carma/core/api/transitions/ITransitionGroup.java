/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.core.api.transitions;


public interface ITransitionGroup {

	public ITransition[] getTransitions();

	public void setTransitions(ITransition[] transitions);

	public void initWithDefaultTransitions();

	public String getName();

}
