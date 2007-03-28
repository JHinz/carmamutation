package com.mutation.transform.bcel;


import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.bcel.generic.InstructionHandle;

import com.mutation.EMutationInstruction;

public class IFEQ_Mutator implements IMutator {

	public void performMutation(InstructionHandle handle) {
		if (!(handle.getInstruction() instanceof IF_ACMPEQ)) {
			return;
		}
		IF_ACMPEQ instruction = (IF_ACMPEQ) handle.getInstruction();
		handle.setInstruction(new IF_ACMPEQ(instruction.getTarget()));

	}

	public EMutationInstruction getMutationOperator() {
		return EMutationInstruction.IF_ACMPEQ;
	}

}
