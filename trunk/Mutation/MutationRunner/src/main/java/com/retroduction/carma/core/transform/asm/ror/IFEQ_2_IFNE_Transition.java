package com.retroduction.carma.core.transform.asm.ror;

import org.objectweb.asm.Opcodes;

public class IFEQ_2_IFNE_Transition extends ROR_Transition {

	public IFEQ_2_IFNE_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFEQ;
		this.targetInstruction = Opcodes.IFNE;
	}

	public String getName() {
		return "IFEQ_to_IFNE";
	}
}
