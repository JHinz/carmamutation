package com.mutation.transform.asm.transitiongroups;

import com.mutation.transform.asm.transitions.VOR_Transition;
import com.mutation.transform.asm.transitions.vor.PLUS_2_MINUS_Transition;

public class VOR_TransitionGroup {

	private VOR_Transition[] transitions;

	public VOR_TransitionGroup() {
		super();
		this.transitions = new VOR_Transition[] { new PLUS_2_MINUS_Transition() };
	}

	public VOR_Transition[] getTransitions() {
		return transitions;
	}

	public void setTransitions(VOR_Transition[] transitions) {
		this.transitions = transitions;
	}

}
