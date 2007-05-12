package com.mutation.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import junit.framework.TestCase;

import com.mutation.runner.IClassSetResolver.ClassDescription;
import com.mutation.runner.events.IEvent;
import com.mutation.runner.events.IEventListener;
import com.mutation.runner.events.MutantsGenerated;
import com.mutation.runner.events.ProcessingClassUnderTest;
import com.mutation.runner.events.ProcessingClassUnderTestFinished;
import com.mutation.runner.events.ProcessingMutant;
import com.mutation.runner.events.ProcessingMutationOperator;
import com.mutation.runner.events.TestsExecuted;
import com.mutation.runner.utililties.ByteCodeFileReader;
import com.mutation.testrunner.JUnitRunner;
import com.mutation.transform.AbstractTransitionGroup;
import com.mutation.transform.MutantGenerator;
import com.mutation.transform.asm.ror.ROR_TransitionGroup;

public class MutationRunnerTestCase extends TestCase {

	public class TestEventListener implements IEventListener {

		private Vector<IEvent> eventList = new Vector<IEvent>();

		public void notifyEvent(IEvent event) {
			eventList.add(event);
		}

		public void reset() {
			this.eventList = new Vector<IEvent>();
		}

	}

	public void test_performMutations_Sample_SampleTestCase() throws Exception {

		TestEventListener listener = new TestEventListener();

		MutantGenerator generator = new MutantGenerator();

		JUnitRunner testRunner = new JUnitRunner();
		testRunner.setStopOnFirstFailedTest(false);

		List<File> testClassFiles = new ArrayList<File>();
		testClassFiles.add(new File("src/test/it/it0001"));

		testRunner.setTestClassesLocationsAsFiles(testClassFiles);

		MutationRunner runner = new MutationRunner();

		runner.setEventListener(listener);
		runner.setMutantGenerator(generator);
		runner.setTestRunner(testRunner);
		runner.setOriginalClassPath(new File("src/test/it/it0001"));

		List<AbstractTransitionGroup> mutationOperators = new ArrayList<AbstractTransitionGroup>();
		mutationOperators.add(new ROR_TransitionGroup(true));

		ByteCodeFileReader bcReader = new ByteCodeFileReader();

		ClassDescription classDescription = new ClassDescription();
		classDescription.setClassName("Sample");
		classDescription.setPackageName("sources");

		Set<String> testNames = new HashSet<String>();
		testNames.add("testsources.SampleTestCase");

		runner.performMutations(mutationOperators, bcReader, classDescription, testNames);

		assertEquals("Wrong number of events fired during mutation run", 10, listener.eventList.size());

		assertTrue("Wrong event", listener.eventList.get(0) instanceof ProcessingClassUnderTest);
		assertEquals("Sample", ((ProcessingClassUnderTest) listener.eventList.get(0)).getClassUnderTest()
				.getClassName());
		assertEquals("sources", ((ProcessingClassUnderTest) listener.eventList.get(0)).getClassUnderTest()
				.getPackageName());
		assertNull(((ProcessingClassUnderTest) listener.eventList.get(0)).getClassUnderTest().getClassFile());

		assertTrue("Wrong event", listener.eventList.get(1) instanceof ProcessingMutationOperator);
		// assertEquals("ROR", ((ProcessingMutationOperator)
		// listener.eventList.get(1)).getOperatorName());

		assertTrue(listener.eventList.get(2) instanceof MutantsGenerated);
		assertEquals("sources.Sample", ((MutantsGenerated) listener.eventList.get(2)).getClassUnderTest());
		// assertEquals(EMutationOperator.ROR, ((MutantsGenerated)
		// listener.eventList.get(2)).getOperator());
		assertEquals(3, ((MutantsGenerated) listener.eventList.get(2)).getGeneratedMutants().size());

		int crcMutant1 = ByteCodeFileReader.calculateByteCodeCRC(((MutantsGenerated) listener.eventList.get(2))
				.getGeneratedMutants().get(0).getByteCode());
		int crcMutant2 = ByteCodeFileReader.calculateByteCodeCRC(((MutantsGenerated) listener.eventList.get(2))
				.getGeneratedMutants().get(1).getByteCode());
		int crcMutant3 = ByteCodeFileReader.calculateByteCodeCRC(((MutantsGenerated) listener.eventList.get(2))
				.getGeneratedMutants().get(2).getByteCode());

		assertTrue(crcMutant1 != crcMutant2);
		assertTrue(crcMutant1 != crcMutant3);
		assertTrue(crcMutant2 != crcMutant3);

		// TODO: the following three statements should show that the survived
		// attribute has not been "calculated" yet --> snapshot of current event
		// which cannot be changed due to later processing steps

		assertFalse(((MutantsGenerated) listener.eventList.get(2)).getGeneratedMutants().get(0).isSurvived());
		assertFalse(((MutantsGenerated) listener.eventList.get(2)).getGeneratedMutants().get(1).isSurvived());
		assertTrue(((MutantsGenerated) listener.eventList.get(2)).getGeneratedMutants().get(2).isSurvived());
		// TODO: investigate more the generated mutants

		assertTrue("Wrong event", listener.eventList.get(3) instanceof ProcessingMutant);
		assertFalse(((ProcessingMutant) listener.eventList.get(3)).getMutant().isSurvived()); // shoud_be_assertTrue

		assertTrue("Wrong event", listener.eventList.get(4) instanceof TestsExecuted);
		assertFalse(((TestsExecuted) listener.eventList.get(4)).isMutantSurvived());
		assertEquals(1, ((TestsExecuted) listener.eventList.get(4)).getKillerTestNames().size());
		assertEquals("testsources.SampleTestCase", ((TestsExecuted) listener.eventList.get(6)).getKillerTestNames()
				.iterator().next());

		assertTrue("Wrong event", listener.eventList.get(5) instanceof ProcessingMutant);
		assertFalse(((ProcessingMutant) listener.eventList.get(5)).getMutant().isSurvived()); // shoud_be_assertTrue

		assertTrue("Wrong event", listener.eventList.get(6) instanceof TestsExecuted);
		assertFalse(((TestsExecuted) listener.eventList.get(6)).isMutantSurvived());
		assertEquals(1, ((TestsExecuted) listener.eventList.get(6)).getKillerTestNames().size());
		assertEquals("testsources.SampleTestCase", ((TestsExecuted) listener.eventList.get(6)).getKillerTestNames()
				.iterator().next());

		assertTrue("Wrong event", listener.eventList.get(7) instanceof ProcessingMutant);
		assertTrue(((ProcessingMutant) listener.eventList.get(7)).getMutant().isSurvived()); // shoud_be_assertTrue

		assertTrue("Wrong event", listener.eventList.get(8) instanceof TestsExecuted);
		assertTrue(((TestsExecuted) listener.eventList.get(8)).isMutantSurvived());
		assertEquals(0, ((TestsExecuted) listener.eventList.get(8)).getKillerTestNames().size());

		assertTrue("Wrong event", listener.eventList.get(9) instanceof ProcessingClassUnderTestFinished);
	}

}
