package com.retroduction.carma.transformer.asm.aor;

import org.objectweb.asm.Opcodes;

public class ISUB_2_IADD_Transition extends AOR_Transition {

	public ISUB_2_IADD_Transition() {
		super();
		this.sourceInstruction = Opcodes.ISUB;
		this.targetInstruction = Opcodes.IADD;
	}

	public String getName() {
		return "ISUB_to_ISUB";
	}
}
