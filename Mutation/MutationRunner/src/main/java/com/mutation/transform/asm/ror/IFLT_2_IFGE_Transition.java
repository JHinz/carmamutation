package com.mutation.transform.asm.ror;

import org.objectweb.asm.Opcodes;

public class IFLT_2_IFGE_Transition extends ROR_Transition {

	public IFLT_2_IFGE_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFLT;
		this.targetInstruction = Opcodes.IFGE;
	}

	public String getName() {
		return "IFLT_to_IFGE";
	}
}
