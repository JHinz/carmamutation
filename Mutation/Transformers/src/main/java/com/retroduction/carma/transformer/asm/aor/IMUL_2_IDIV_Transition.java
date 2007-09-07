package com.retroduction.carma.transformer.asm.aor;

import org.objectweb.asm.Opcodes;

public class IMUL_2_IDIV_Transition extends AOR_Transition {

	public IMUL_2_IDIV_Transition() {
		super();
		this.sourceInstruction = Opcodes.IADD;
		this.targetInstruction = Opcodes.ISUB;
	}

	public String getName() {
		return "IADD_to_ISUB";
	}
}
