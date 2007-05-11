package com.mutation.transform.asm.ror;

import com.mutation.transform.ITransition;
import com.mutation.transform.ITransitionGroup;

public class ROR_TransitionGroup extends ITransitionGroup {

	private ROR_Transition[] transitions;

	public ROR_TransitionGroup(boolean useDefaultTransitions) {
		super(useDefaultTransitions);
		if (useDefaultTransitions) {
			initWithDefaultTransitions();
		}
	}

	public ROR_Transition[] getTransitions() {
		return transitions;
	}

	public void setTransitions(ITransition[] transitions) {
		this.transitions = (ROR_Transition[]) transitions;
	}

	public String getName() {
		return "ROR";
	}

	public void initWithDefaultTransitions() {
		this.transitions = new ROR_Transition[] { new IF_ICMPEQ_2_IF_ICMPNE_Transition(),
				new IF_ICMPNE_2_IF_ICMPEQ_Transition(), new IFEQ_2_IFNE_Transition(), new IFNE_2_IFEQ_Transition() };
	}

}
