package com.mutation.transform.asm.ror;

import com.mutation.transform.ITransition;
import com.mutation.transform.AbstractTransitionGroup;

public class ROR_TransitionGroup extends AbstractTransitionGroup {

	private AbstractASMTransition[] transitions;

	public ROR_TransitionGroup(boolean useDefaultTransitions) {
		super(useDefaultTransitions);
		if (useDefaultTransitions) {
			initWithDefaultTransitions();
		}
	}

	public AbstractASMTransition[] getTransitions() {
		return transitions;
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
				new IFLT_2_IFGE_Transition() };
	}

}
