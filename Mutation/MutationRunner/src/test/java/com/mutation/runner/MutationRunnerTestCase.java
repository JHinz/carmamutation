package com.mutation.runner;

import java.io.File;
import java.net.URL;
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
import com.mutation.transform.asm.MutantGenerator;

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

	public void test_performMutations() throws Exception {

		TestEventListener listener = new TestEventListener();

		MutantGenerator generator = new MutantGenerator();
		generator.setOriginalClassPath(new File("src/test/it/it0001/"));

		JUnitRunner testRunner = new JUnitRunner();
		testRunner.setStopOnFirstFailedTest(false);
		
		List<File> testClassFiles = new ArrayList<File>();
		testClassFiles.add(new File("src/test/it/it0001/"));
		
		testRunner.setTestClassesLocationsAsFiles(testClassFiles);

		MutationRunner runner = new MutationRunner();

		runner.setEventListener(listener);
		runner.setMutantGenerator(generator);
		runner.setTestRunner(testRunner);
		runner.setOriginalClassPath(new File("src/test/it/it0001/"));

		List<EMutationOperator> mutationOperators = new ArrayList<EMutationOperator>();
		mutationOperators.add(EMutationOperator.ROR);

		ByteCodeFileReader bcReader = new ByteCodeFileReader();

		ClassDescription classDescription = new ClassDescription();
		classDescription.setClassName("sources.Sample");
		classDescription.setPackageName("");

		Set<String> testNames = new HashSet<String>();
		testNames.add("testsources.SampleTestCase");

		runner.performMutations(mutationOperators, bcReader, classDescription, testNames);

		assertEquals(10, listener.eventList.size());
		assertTrue(listener.eventList.get(0) instanceof ProcessingClassUnderTest);
		assertTrue(listener.eventList.get(1) instanceof ProcessingMutationOperator);
		assertTrue(listener.eventList.get(2) instanceof MutantsGenerated);
		assertTrue(listener.eventList.get(3) instanceof ProcessingMutant);
		assertTrue(listener.eventList.get(4) instanceof TestsExecuted);
		assertTrue(listener.eventList.get(5) instanceof ProcessingMutant);
		assertTrue(listener.eventList.get(6) instanceof TestsExecuted);
		assertTrue(listener.eventList.get(7) instanceof ProcessingMutant);
		assertTrue(listener.eventList.get(8) instanceof TestsExecuted);
		assertTrue(listener.eventList.get(9) instanceof ProcessingClassUnderTestFinished);
	}

}
