package com.mutation.transform.asm.ror;

import org.objectweb.asm.Opcodes;


public class IF_ICMPLE_2_IF_ICMPGT_Transition extends ROR_Transition {

	public IF_ICMPLE_2_IF_ICMPGT_Transition() {
		super();
		this.sourceInstruction = Opcodes.IF_ICMPLE;
		this.targetInstruction = Opcodes.IF_ICMPGT;
	}

}
