package com.retroduction.carma.transformer.asm.ror;

import org.objectweb.asm.Opcodes;

public class IFGE_2_IFLT_Transition extends ROR_Transition {

	public IFGE_2_IFLT_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFGE;
		this.targetInstruction = Opcodes.IFLT;
	}

	public String getName() {
		return "IFGE_to_IFLT";
	}
}
