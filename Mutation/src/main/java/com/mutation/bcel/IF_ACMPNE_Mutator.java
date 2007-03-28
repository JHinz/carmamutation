package com.mutation.bcel;


import org.apache.bcel.generic.IF_ACMPNE;
import org.apache.bcel.generic.InstructionHandle;

import com.mutation.EMutationInstruction;

public class IF_ACMPNE_Mutator implements IMutator {

	public void performMutation(InstructionHandle handle) {
		if (!(handle.getInstruction() instanceof IF_ACMPNE)) {
			return;
		}
		IF_ACMPNE instruction = (IF_ACMPNE) handle.getInstruction();
		handle.setInstruction(new IF_ACMPNE(instruction.getTarget()));

	}

	public EMutationInstruction getMutationOperator() {
		return EMutationInstruction.IF_ACMPNE;
	}

}
