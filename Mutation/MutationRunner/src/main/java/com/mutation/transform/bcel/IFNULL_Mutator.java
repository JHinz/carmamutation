package com.mutation.transform.bcel;


import org.apache.bcel.generic.IFNULL;
import org.apache.bcel.generic.InstructionHandle;

import com.mutation.runner.EMutationInstruction;

public class IFNULL_Mutator implements IMutator {

	public void performMutation(InstructionHandle handle) {
		if (!(handle.getInstruction() instanceof IFNULL)) {
			return;
		}
		IFNULL instruction = (IFNULL) handle.getInstruction();
		handle.setInstruction(new IFNULL(instruction.getTarget()));

	}

	public EMutationInstruction getMutationOperator() {
		return EMutationInstruction.IFNULL;
	}

}
