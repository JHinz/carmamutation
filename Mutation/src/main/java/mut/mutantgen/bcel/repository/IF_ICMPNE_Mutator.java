package mut.mutantgen.bcel.repository;

import mut.EMutationOperator;

import org.apache.bcel.generic.IF_ICMPEQ;
import org.apache.bcel.generic.IF_ICMPNE;
import org.apache.bcel.generic.InstructionHandle;

public class IF_ICMPNE_Mutator implements IMutator {

	public void performMutation(InstructionHandle handle) {
		if (!(handle.getInstruction() instanceof IF_ICMPNE)) {
			return;
		}
		IF_ICMPNE instruction = (IF_ICMPNE) handle.getInstruction();
		handle.setInstruction(new IF_ICMPEQ(instruction.getTarget()));
	}

	public EMutationOperator getMutationOperator() {
		return EMutationOperator.IF_ICMPNE;
	}

}
