package com.retroduction.carma.transformer.asm.aor;

import org.objectweb.asm.Opcodes;

public class DDIV_2_DMUL_Transition extends AOR_Transition {

	public DDIV_2_DMUL_Transition() {
		super();
		this.sourceInstruction = Opcodes.DSUB;
		this.targetInstruction = Opcodes.DADD;
	}

	public String getName() {
		return "DSUB_to_DADD";
	}
}
