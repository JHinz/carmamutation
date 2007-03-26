package mut.mutantgen.bcel.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mut.EMutationOperator;
import mut.EMutationType;

public class MutationRepository {

	HashMap<EMutationType, List<EMutationOperator>> mutationOperatorMap;

	public MutationRepository() {
		mutationOperatorMap = new HashMap<EMutationType, List<EMutationOperator>>();

		List<EMutationOperator> operators = new ArrayList<EMutationOperator>();
		operators.add(EMutationOperator.IF_ICMPNE);
		operators.add(EMutationOperator.IF_ICMPEQ);
		operators.add(EMutationOperator.IF_ACMPNE);
		operators.add(EMutationOperator.IF_ACMPEQ);
		operators.add(EMutationOperator.IFNE);
		operators.add(EMutationOperator.IFEQ);
		operators.add(EMutationOperator.IFNULL);
		operators.add(EMutationOperator.IFNONNULL);
		mutationOperatorMap.put(EMutationType.ROR, operators);
	}

	public List<EMutationOperator> getOperatorMapping(EMutationType eMutation) {
		return mutationOperatorMap.get(eMutation);
	}

	public IMutator getMutator(EMutationOperator operator) {
		switch (operator) {
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
			throw new RuntimeException("No appropriate mutator known - Implementation Bug");
		}
	}

}