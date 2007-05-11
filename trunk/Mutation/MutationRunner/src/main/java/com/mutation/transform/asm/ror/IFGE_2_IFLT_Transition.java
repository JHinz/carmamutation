package com.mutation.transform.asm.ror;

import org.objectweb.asm.Opcodes;

public class IFGE_2_IFLT_Transition extends ROR_Transition {

	public IFGE_2_IFLT_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFLT;
		this.targetInstruction = Opcodes.IFGE;
	}

}
