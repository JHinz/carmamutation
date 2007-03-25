package mut.mutantgen.bcel.repository;

import java.util.List;

import junit.framework.TestCase;
import mut.EMutationOperator;
import mut.EMutationType;

public class RepositoryTestCase extends TestCase {

	public void test_Mappings() {
		MutationRepository repo = new MutationRepository();
		List<EMutationOperator> operators = repo.getOperatorMapping(EMutationType.ROR);
		assertEquals("Invalid number of operators for Mapping ROR", 6, operators.size());
		assertEquals("Invalid number of operators for Mapping ROR", EMutationOperator.IF_ICMPNE, operators.get(0));
		assertEquals("Invalid number of operators for Mapping ROR", EMutationOperator.IF_ICMPEQ, operators.get(1));
		assertEquals("Invalid number of operators for Mapping ROR", EMutationOperator.IF_ACMPNE, operators.get(2));
		assertEquals("Invalid number of operators for Mapping ROR", EMutationOperator.IF_ACMPEQ, operators.get(3));
		assertEquals("Invalid number of operators for Mapping ROR", EMutationOperator.IFNE, operators.get(4));
		assertEquals("Invalid number of operators for Mapping ROR", EMutationOperator.IFEQ, operators.get(5));
	}
}
