package mut.mutantgen.bcel.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import mut.EMutationOperator;
import mut.EMutationType;
import mut.Mutant;
import mut.mutantgen.bcel.BCELMutantCreator;

public class MultipleMethods_TestCase extends TestCase {

	// FileWriter writer = new FileWriter("mod.class");
	// writer.write(new String(byteCode[0]));
	// writer.flush();
	// writer.close();

	private static final String TEMPLATE_CLASS_NAME = "mut.mutantgen.bcel.common.MultipleMethods_TemplateClass";

	public void test_IF_CMPNE_to_IF_CMPEQ_CheckNumberOfMutants() throws Exception {

		BCELMutantCreator bcel = new BCELMutantCreator();

		Set<EMutationType> operators = new HashSet<EMutationType>();
		operators.add(EMutationType.ROR);

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, operators);

		assertEquals("Number of first level mutants incorrect", 4, mutants.size());

		int numberOf_IF_ICMPNE = 0;
		int numberOf_IF_ICMPEQ = 0;

		for (Mutant mutant : mutants) {
			if (mutant.getMutationOperator() == EMutationOperator.IF_ICMPEQ)
				numberOf_IF_ICMPEQ++;
			if (mutant.getMutationOperator() == EMutationOperator.IF_ICMPNE)
				numberOf_IF_ICMPNE++;
		}

		assertEquals("Number of IF_ICMPNE found does not match", 2, numberOf_IF_ICMPNE);
		assertEquals("Number of IF_ICMPEQ found does not match", 2, numberOf_IF_ICMPEQ);
	}

}
