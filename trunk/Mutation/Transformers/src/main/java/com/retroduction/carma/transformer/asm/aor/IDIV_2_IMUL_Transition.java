package com.retroduction.carma.transformer.asm.aor;

import org.objectweb.asm.Opcodes;

public class IDIV_2_IMUL_Transition extends AOR_Transition {

	public IDIV_2_IMUL_Transition() {
		super();
		this.sourceInstruction = Opcodes.ISUB;
		this.targetInstruction = Opcodes.IADD;
	}

	public String getName() {
		return "ISUB_to_ISUB";
	}
}
