package com.mutation.transform.asm.transitions.ror;

import com.mutation.transform.asm.transitions.ROR_Transition;

public class IF_ICMPNE_2_IF_ICMPEQ_Transition extends ROR_Transition {

	public IF_ICMPNE_2_IF_ICMPEQ_Transition() {
		super();
		this.sourceInstruction = "IF_ICMPNE";
		this.targetInstruction = "IF_ICMPEQ";

	}

}
