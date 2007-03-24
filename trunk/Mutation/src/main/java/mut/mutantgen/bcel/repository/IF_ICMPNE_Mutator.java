package mut.mutantgen.bcel.repository;

import org.apache.bcel.generic.IF_ICMPEQ;
import org.apache.bcel.generic.IF_ICMPNE;
import org.apache.bcel.generic.InstructionHandle;

public class IF_ICMPNE_Mutator implements Mutator {

	public void performMutation_IF_ICMPNE(InstructionHandle handle) {
		IF_ICMPNE instruction = (IF_ICMPNE) handle.getInstruction();
		handle.setInstruction(new IF_ICMPEQ(instruction.getTarget()));
	}

}
