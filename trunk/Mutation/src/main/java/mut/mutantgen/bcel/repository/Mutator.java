package mut.mutantgen.bcel.repository;

import org.apache.bcel.generic.InstructionHandle;

public interface Mutator {

	public void performMutation_IF_ICMPNE(InstructionHandle handle);

}
