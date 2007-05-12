package com.mutation.transform.asm.ror;

import org.objectweb.asm.Opcodes;

public class IFNE_2_IFEQ_Transition extends ROR_Transition {

	public IFNE_2_IFEQ_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFNE;
		this.targetInstruction = Opcodes.IFEQ;
	}

	public String getName() {
		return "IFNE_to_IFEQ";
	}
}
