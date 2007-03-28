package mut.mutantgen.bcel.repository;

import java.util.List;

import com.mutation.EMutationInstruction;
import com.mutation.EMutationOperator;

import junit.framework.TestCase;

public class RepositoryTestCase extends TestCase {

	public void test_Mappings() {
		MutationRepository repo = new MutationRepository();
		List<EMutationInstruction> operators = repo.getOperatorMapping(EMutationOperator.ROR);
		assertEquals("Invalid number of operators for Mapping ROR", 8, operators.size());
		assertEquals("Invalid number of operators for Mapping ROR", EMutationInstruction.IF_ICMPNE, operators.get(0));
		assertEquals("Invalid number of operators for Mapping ROR", EMutationInstruction.IF_ICMPEQ, operators.get(1));
		assertEquals("Invalid number of operators for Mapping ROR", EMutationInstruction.IF_ACMPNE, operators.get(2));
		assertEquals("Invalid number of operators for Mapping ROR", EMutationInstruction.IF_ACMPEQ, operators.get(3));
		assertEquals("Invalid number of operators for Mapping ROR", EMutationInstruction.IFNE, operators.get(4));
		assertEquals("Invalid number of operators for Mapping ROR", EMutationInstruction.IFEQ, operators.get(5));
		assertEquals("Invalid number of operators for Mapping ROR", EMutationInstruction.IFNULL, operators.get(6));
		assertEquals("Invalid number of operators for Mapping ROR", EMutationInstruction.IFNONNULL, operators.get(7));
	}
}
