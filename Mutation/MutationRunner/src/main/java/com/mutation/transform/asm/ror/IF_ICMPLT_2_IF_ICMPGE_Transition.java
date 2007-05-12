package com.mutation.transform.asm.ror;

import org.objectweb.asm.Opcodes;


public class IF_ICMPLT_2_IF_ICMPGE_Transition extends ROR_Transition {

	public IF_ICMPLT_2_IF_ICMPGE_Transition() {
		super();
		this.sourceInstruction = Opcodes.IF_ICMPLT;
		this.targetInstruction = Opcodes.IF_ICMPGE;
	}

}
