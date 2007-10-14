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
