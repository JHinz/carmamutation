package com.retroduction.carma.transformer.asm.ror;

import org.objectweb.asm.Opcodes;

public class IFLE_2_IFGT_Transition extends ROR_Transition {

	public IFLE_2_IFGT_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFLE;
		this.targetInstruction = Opcodes.IFGT;
	}

	public String getName() {
		return "IFLE_to_IFGT";
	}
}
