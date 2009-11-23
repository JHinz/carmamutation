/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */

package com.retroduction.carma.transformer.asm.aor;

import com.retroduction.carma.core.api.transitions.ITransition;
import com.retroduction.carma.core.api.transitions.ITransitionGroup;
import com.retroduction.carma.transformer.asm.AbstractASMTransition;

public class AOR_TransitionGroup implements ITransitionGroup {

	private AbstractASMTransition[] transitions;

	public AOR_TransitionGroup(boolean useDefaultTransitions) {
		if (useDefaultTransitions) {
			this.initWithDefaultTransitions();
		}
	}

	public AbstractASMTransition[] getTransitions() {
		return this.transitions;
	}

	public void setTransitions(ITransition[] transitions) {
		this.transitions = (AbstractASMTransition[]) transitions;
	}

	public String getName() {
		return "AOR";
	}

	public void initWithDefaultTransitions() {
		this.transitions = new AbstractASMTransition[] { new IADD_2_ISUB_Transition(), new ISUB_2_IADD_Transition(),
				new DADD_2_DSUB_Transition(), new DSUB_2_DADD_Transition(), new IMUL_2_IDIV_Transition(),
				new IDIV_2_IMUL_Transition(), new DMUL_2_DDIV_Transition(), new DDIV_2_DMUL_Transition() };
	}
}
