package mut.mutantgen.bcel.repository;

import mut.EMutationOperator;

import org.apache.bcel.generic.IFEQ;
import org.apache.bcel.generic.IFNE;
import org.apache.bcel.generic.InstructionHandle;

public class IFNE_Mutator implements IMutator {

	public void performMutation(InstructionHandle handle) {
		if (!(handle.getInstruction() instanceof IFNE)) {
			return;
		}
		IFNE instruction = (IFNE) handle.getInstruction();
		handle.setInstruction(new IFEQ(instruction.getTarget()));

	}

	public EMutationOperator getMutationOperator() {
		return EMutationOperator.IFNE;
	}

}
