package mut.mutantgen.bcel.repository;

import mut.EMutationOperator;

import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.bcel.generic.InstructionHandle;

public class IF_ACMPEQ_Mutator implements IMutator {

	public void performMutation(InstructionHandle handle) {
		if (!(handle.getInstruction() instanceof IF_ACMPEQ)) {
			return;
		}
		IF_ACMPEQ instruction = (IF_ACMPEQ) handle.getInstruction();
		handle.setInstruction(new IF_ACMPEQ(instruction.getTarget()));

	}

	public EMutationOperator getMutationOperator() {
		return EMutationOperator.IF_ACMPEQ;
	}

}
