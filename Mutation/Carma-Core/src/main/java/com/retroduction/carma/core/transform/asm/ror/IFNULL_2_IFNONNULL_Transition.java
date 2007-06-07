package com.retroduction.carma.core.transform.asm.ror;

import org.objectweb.asm.Opcodes;

public class IFNULL_2_IFNONNULL_Transition extends ROR_Transition {

	public IFNULL_2_IFNONNULL_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFNULL;
		this.targetInstruction = Opcodes.IFNONNULL;
	}

	public String getName() {
		return "IFNULL_to_IFNONNULL";
	}
}
