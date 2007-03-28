package mut.mutantgen.bcel.common;

import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;

import com.mutation.EMutationInstruction;
import com.mutation.EMutationOperator;
import com.mutation.Mutant;
import com.mutation.events.IEvent;
import com.mutation.events.IEventListener;
import com.mutation.transform.bcel.MutantGenerator;

public class MultipleMethods_TestCase extends TestCase {

	// FileWriter writer = new FileWriter("mod.class");
	// writer.write(new String(byteCode[0]));
	// writer.flush();
	// writer.close();

	private static final String TEMPLATE_CLASS_NAME = "mut.mutantgen.bcel.common.MultipleMethods_TemplateClass";

	public void test_IF_CMPNE_to_IF_CMPEQ_CheckNumberOfMutants() throws Exception {

		EventListenerMock listenerMock = new EventListenerMock();
		MutantGenerator bcel = new MutantGenerator();

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, EMutationOperator.ROR, listenerMock);

		assertEquals("Number of first level mutants incorrect", 4, mutants.size());

		int numberOf_IF_ICMPNE = 0;
		int numberOf_IF_ICMPEQ = 0;

		for (Mutant mutant : mutants) {
			if (mutant.getMutationOperator() == EMutationInstruction.IF_ICMPEQ)
				numberOf_IF_ICMPEQ++;
			if (mutant.getMutationOperator() == EMutationInstruction.IF_ICMPNE)
				numberOf_IF_ICMPNE++;
		}

		assertEquals("Number of IF_ICMPNE found does not match", 2, numberOf_IF_ICMPNE);
		assertEquals("Number of IF_ICMPEQ found does not match", 2, numberOf_IF_ICMPEQ);
		
		assertEquals("Wrong number of events fired.", 1, listenerMock.events.size());
		//TODO: add more detailed assertions for events
	}

}
