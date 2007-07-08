package com.retroduction.carma.transformer.asm.ror;

import org.objectweb.asm.Opcodes;

public class IF_ACMPEQ_2_IF_ACMPNE_Transition extends ROR_Transition {

	public IF_ACMPEQ_2_IF_ACMPNE_Transition() {
		super();
		this.sourceInstruction = Opcodes.IF_ACMPEQ;
		this.targetInstruction = Opcodes.IF_ACMPNE;
	}

	public String getName() {
		return "IF_ACMPEQ_to_IF_ACMPNE";
	}

}
