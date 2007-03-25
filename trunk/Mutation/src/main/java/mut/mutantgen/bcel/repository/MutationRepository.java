package mut.mutantgen.bcel.repository;

public class MutationRepository {

	public enum EMutation {
		IF_ICMPNE
	}

	public IMutator getMutator(EMutation mutation) {
		switch (mutation) {
		case IF_ICMPNE:
			return new IF_ICMPNE_Mutator();
		default:
			throw new RuntimeException(
					"No appropriate mutator known - Implementation Bug");
		}
	}

}
