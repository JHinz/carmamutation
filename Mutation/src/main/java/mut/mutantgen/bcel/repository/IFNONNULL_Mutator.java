package mut.mutantgen.bcel.repository;


import org.apache.bcel.generic.IFNONNULL;
import org.apache.bcel.generic.InstructionHandle;

import com.mutation.EMutationInstruction;

public class IFNONNULL_Mutator implements IMutator {

	public void performMutation(InstructionHandle handle) {
		if (!(handle.getInstruction() instanceof IFNONNULL)) {
			return;
		}
		IFNONNULL instruction = (IFNONNULL) handle.getInstruction();
		handle.setInstruction(new IFNONNULL(instruction.getTarget()));

	}

	public EMutationInstruction getMutationOperator() {
		return EMutationInstruction.IFNONNULL;
	}

}
