package com.retroduction.carma.transformer.asm.aor;

import org.objectweb.asm.Opcodes;

public class IDIV_2_IMUL_Transition extends AOR_Transition {

	public IDIV_2_IMUL_Transition() {
		super();
		this.sourceInstruction = Opcodes.IDIV;
		this.targetInstruction = Opcodes.IMUL;
	}

	public String getName() {
		return "IDIV_to_IMUL";
	}
}
