package com.mutation.bcel;

import com.mutation.EMutationInstruction;

public class BCELMutatorRepository {

	public IMutator getMutator(EMutationInstruction instruction) {
		switch (instruction) {
		case IF_ICMPNE:
			return new IF_ICMPNE_Mutator();
		case IF_ICMPEQ:
			return new IF_ICMPEQ_Mutator();
		case IF_ACMPNE:
			return new IF_ACMPNE_Mutator();
		case IF_ACMPEQ:
			return new IF_ACMPEQ_Mutator();
		case IFNE:
			return new IFNE_Mutator();
		case IFEQ:
			return new IFEQ_Mutator();
		case IFNULL:
			return new IFNULL_Mutator();
		case IFNONNULL:
			return new IFNONNULL_Mutator();
		default:
			throw new RuntimeException(
					"No appropriate mutator known - Implementation Bug");
		}
	}

}
