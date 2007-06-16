package com.retroduction.carma.transformer.asm.ror;

import org.objectweb.asm.Opcodes;

public class IFGT_2_IFLE_Transition extends ROR_Transition {

	public IFGT_2_IFLE_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFGT;
		this.targetInstruction = Opcodes.IFLE;
	}

	public String getName() {
		return "IFGT_to_IFLE";
	}
}
