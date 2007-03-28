package mut.mutantgen.bcel.repository;


import org.apache.bcel.generic.InstructionHandle;

import com.mutation.EMutationInstruction;

public interface IMutator {

	public void performMutation(InstructionHandle handle);

	public EMutationInstruction getMutationOperator();

}
