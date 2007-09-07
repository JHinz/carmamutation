package com.retroduction.carma.transformer.asm.aor;

import org.objectweb.asm.Opcodes;

public class DMUL_2_DDIV_Transition extends AOR_Transition {

	public DMUL_2_DDIV_Transition() {
		super();
		this.sourceInstruction = Opcodes.DADD;
		this.targetInstruction = Opcodes.DSUB;
	}

	public String getName() {
		return "DADD_to_DSUB";
	}
}
