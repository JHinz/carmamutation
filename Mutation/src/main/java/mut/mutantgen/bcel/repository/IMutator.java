package mut.mutantgen.bcel.repository;

import mut.EMutationOperator;

import org.apache.bcel.generic.InstructionHandle;

public interface IMutator {

	public void performMutation(InstructionHandle handle);

	public EMutationOperator getMutationOperator();

}
