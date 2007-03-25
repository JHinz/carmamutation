package mut.mutantgen.bcel.repository;

import org.apache.bcel.generic.InstructionHandle;

public interface IMutator {

	public void performMutation_IF_ICMPNE(InstructionHandle handle);

}
