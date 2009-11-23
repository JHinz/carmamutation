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

package com.retroduction.carma.transformer.asm.ror;

import com.retroduction.carma.core.api.transitions.ITransition;
import com.retroduction.carma.core.api.transitions.ITransitionGroup;
import com.retroduction.carma.transformer.asm.AbstractASMTransition;

public class ROR_TransitionGroup implements ITransitionGroup {

	private AbstractASMTransition[] transitions;

	public ROR_TransitionGroup(boolean useDefaultTransitions) {
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
		return "ROR";
	}

	public void initWithDefaultTransitions() {
		this.transitions = new AbstractASMTransition[] { new IF_ACMPEQ_2_IF_ACMPNE_Transition(),
				new IF_ACMPNE_2_IF_ACMPEQ_Transition(), new IF_ICMPEQ_2_IF_ICMPNE_Transition(),
				new IF_ICMPNE_2_IF_ICMPEQ_Transition(), new IF_ICMPGE_2_IF_ICMPLT_Transition(),
				new IF_ICMPLT_2_IF_ICMPGE_Transition(), new IF_ICMPGT_2_IF_ICMPLE_Transition(),
				new IF_ICMPLE_2_IF_ICMPGT_Transition(), new IFGT_2_IFLE_Transition(), new IFLE_2_IFGT_Transition(),
				new IFEQ_2_IFNE_Transition(), new IFNE_2_IFEQ_Transition(), new IFGE_2_IFLT_Transition(),
				new IFLT_2_IFGE_Transition(), new IFNONNULL_2_IFNULL_Transition(), new IFNULL_2_IFNONNULL_Transition() };
	}

}
