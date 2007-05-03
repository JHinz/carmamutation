package com.mutation.transform.bcel;

import java.util.List;

import junit.framework.TestCase;

import org.apache.bcel.Repository;

import com.mutation.runner.EMutationOperator;
import com.mutation.runner.Mutant;
import com.mutation.transform.util.EventListenerMock;

public class MultipleMethods_TestCase extends TestCase {

	// FileWriter writer = new FileWriter("mod.class");
	// writer.write(new String(byteCode[0]));
	// writer.flush();
	// writer.close();

	private static final String TEMPLATE_CLASS_NAME = "com.mutation.transform.bcel.MultipleMethods_TemplateClass";

	public void test_IF_CMPNE_to_IF_CMPEQ_CheckNumberOfMutants() throws Exception {

		EventListenerMock listenerMock = new EventListenerMock();
		MutantGenerator bcel = new MutantGenerator();

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, Repository.lookupClass(TEMPLATE_CLASS_NAME)
				.getBytes(), EMutationOperator.ROR, listenerMock);

		assertEquals("Number of first level mutants incorrect", 4, mutants.size());

		assertEquals("Wrong number of events fired.", 1, listenerMock.getEvents().size());
		// TODO: add more detailed assertions for events
	}

}
