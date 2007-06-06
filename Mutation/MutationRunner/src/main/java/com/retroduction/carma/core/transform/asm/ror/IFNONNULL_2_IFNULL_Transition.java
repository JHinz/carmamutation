package com.retroduction.carma.core.transform.asm.ror;

import org.objectweb.asm.Opcodes;

public class IFNONNULL_2_IFNULL_Transition extends ROR_Transition {

	public IFNONNULL_2_IFNULL_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFNONNULL;
		this.targetInstruction = Opcodes.IFNULL;
	}

	public String getName() {
		return "IFNONNULL_to_IFNULL";
	}
}
