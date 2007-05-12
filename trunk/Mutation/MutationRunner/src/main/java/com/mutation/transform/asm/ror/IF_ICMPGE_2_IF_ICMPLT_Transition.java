package com.mutation.transform.asm.ror;

import org.objectweb.asm.Opcodes;


public class IF_ICMPGE_2_IF_ICMPLT_Transition extends ROR_Transition {

	public IF_ICMPGE_2_IF_ICMPLT_Transition() {
		super();
		this.sourceInstruction = Opcodes.IF_ICMPGE;
		this.targetInstruction = Opcodes.IF_ICMPLT;
	}

}
