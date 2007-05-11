package com.mutation.transform.asm.ror;

import org.objectweb.asm.Opcodes;


public class IF_ICMPEQ_2_IF_ICMPNE_Transition extends ROR_Transition {

	public IF_ICMPEQ_2_IF_ICMPNE_Transition() {
		super();
		this.sourceInstruction = Opcodes.IF_ICMPNE;
		this.targetInstruction = Opcodes.IF_ICMPEQ;
	}

}
