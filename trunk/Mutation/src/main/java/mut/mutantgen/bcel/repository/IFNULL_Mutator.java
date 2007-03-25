package mut.mutantgen.bcel.repository;

import mut.EMutationOperator;

import org.apache.bcel.generic.IFNULL;
import org.apache.bcel.generic.InstructionHandle;

public class IFNULL_Mutator implements IMutator {

	public void performMutation(InstructionHandle handle) {
		if (!(handle.getInstruction() instanceof IFNULL)) {
			return;
		}
		IFNULL instruction = (IFNULL) handle.getInstruction();
		handle.setInstruction(new IFNULL(instruction.getTarget()));

	}

	public EMutationOperator getMutationOperator() {
		return EMutationOperator.IFNULL;
	}

}
