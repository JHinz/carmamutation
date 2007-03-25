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
		default:
			throw new RuntimeException("No appropriate mutator known - Implementation Bug");
		}
	}

}
