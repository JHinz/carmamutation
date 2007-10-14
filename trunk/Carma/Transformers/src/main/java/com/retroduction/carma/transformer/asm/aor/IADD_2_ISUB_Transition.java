package com.retroduction.carma.transformer.asm.aor;

import org.objectweb.asm.Opcodes;

public class IADD_2_ISUB_Transition extends AOR_Transition {

	public IADD_2_ISUB_Transition() {
		super();
		this.sourceInstruction = Opcodes.IADD;
		this.targetInstruction = Opcodes.ISUB;
	}

	public String getName() {
		return "IADD_to_ISUB";
	}
}
