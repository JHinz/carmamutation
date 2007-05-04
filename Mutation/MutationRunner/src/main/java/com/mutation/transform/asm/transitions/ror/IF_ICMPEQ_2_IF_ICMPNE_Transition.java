package com.mutation.transform.asm.transitions.ror;

import com.mutation.transform.asm.transitions.ROR_Transition;

public class IF_ICMPEQ_2_IF_ICMPNE_Transition extends ROR_Transition {

	public IF_ICMPEQ_2_IF_ICMPNE_Transition() {
		super();
		this.sourceInstruction = "IF_ICMPEQ";
		this.targetInstruction = "IF_ICMPNE";

	}

}
