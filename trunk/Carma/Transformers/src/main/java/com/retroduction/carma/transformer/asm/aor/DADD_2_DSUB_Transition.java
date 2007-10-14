package com.retroduction.carma.transformer.asm.aor;

import org.objectweb.asm.Opcodes;

public class DADD_2_DSUB_Transition extends AOR_Transition {

	public DADD_2_DSUB_Transition() {
		super();
		this.sourceInstruction = Opcodes.DADD;
		this.targetInstruction = Opcodes.DSUB;
	}

	public String getName() {
		return "DADD_to_DSUB";
	}
}
