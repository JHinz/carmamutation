package com.mutation.transform.asm.ror;

import org.objectweb.asm.Opcodes;


public class IF_ICMPGT_2_IF_ICMPLE_Transition extends ROR_Transition {

	public IF_ICMPGT_2_IF_ICMPLE_Transition() {
		super();
		this.sourceInstruction = Opcodes.IF_ICMPGT;
		this.targetInstruction = Opcodes.IF_ICMPLE;
	}

	public String getName() {
		return "IF_ICMPGT_to_IF_ICMPLE";
	}
}
