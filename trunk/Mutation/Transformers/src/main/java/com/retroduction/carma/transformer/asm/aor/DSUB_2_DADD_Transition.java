package com.retroduction.carma.transformer.asm.aor;

import org.objectweb.asm.Opcodes;

public class DSUB_2_DADD_Transition extends AOR_Transition {

	public DSUB_2_DADD_Transition() {
		super();
		this.sourceInstruction = Opcodes.DSUB;
		this.targetInstruction = Opcodes.DADD;
	}

	public String getName() {
		return "DSUB_to_DADD";
	}
}
