package com.retroduction.carma.transformer.asm.aor;

import org.objectweb.asm.Opcodes;

public class IMUL_2_IDIV_Transition extends AOR_Transition {

	public IMUL_2_IDIV_Transition() {
		super();
		this.sourceInstruction = Opcodes.IMUL;
		this.targetInstruction = Opcodes.IDIV;
	}

	public String getName() {
		return "IMUL_to_IDIV";
	}
}
