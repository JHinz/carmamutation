package com.mutation.transform.asm.transitiongroups;

import com.mutation.transform.asm.transitions.ROR_Transition;
import com.mutation.transform.asm.transitions.ror.IF_ICMPEQ_2_IF_ICMPNE_Transition;
import com.mutation.transform.asm.transitions.ror.IF_ICMPNE_2_IF_ICMPEQ_Transition;

public class ROR_TransitionGroup {

	private ROR_Transition[] transitions;

	public ROR_TransitionGroup() {
		super();
		this.transitions = new ROR_Transition[] { new IF_ICMPEQ_2_IF_ICMPNE_Transition(),
				new IF_ICMPNE_2_IF_ICMPEQ_Transition() };
	}

	public ROR_Transition[] getTransitions() {
		return transitions;
	}

	public void setTransitions(ROR_Transition[] transitions) {
		this.transitions = transitions;
	}

}
