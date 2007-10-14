package com.retroduction.carma.testrunners.junit3;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

public class JUnitRunnerTestCase extends TestCase {

	private Logger logger = LoggerFactory.getLogger("JUnitRunnerTestCase");

	private class ThreadedMockMutationRunner implements IMutantJUnitRunner {

		private List<Boolean> testCaseIDsWithInfiniteLoop;

		private int numberOfTestCasesExecuted;

		private Object lock;

		private boolean finished;

		private boolean simulateTestRunFailuresWithoutInfiniteLoop;

		private boolean useExceptionForSimulationFailuresWithoutInfiniteLoop;

		public ThreadedMockMutationRunner(boolean simulateTestRunFailuresWithoutInfiniteLoop,
				boolean useExceptionForSimulationFailuresWithoutInfiniteLoop) {
			super();
			this.simulateTestRunFailuresWithoutInfiniteLoop = simulateTestRunFailuresWithoutInfiniteLoop;
			this.useExceptionForSimulationFailuresWithoutInfiniteLoop = useExceptionForSimulationFailuresWithoutInfiniteLoop;
			this.testCaseIDsWithInfiniteLoop = new ArrayList<Boolean>();
			this.numberOfTestCasesExecuted = 0;
		}

		public ThreadedMockMutationRunner(List<Boolean> testCaseIDsWithInfiniteLoop,
				boolean simulateTestRunFailuresWithoutInfiniteLoop,
				boolean useExceptionForSimulationFailuresWithoutInfiniteLoop) {
			super();
			this.simulateTestRunFailuresWithoutInfiniteLoop = simulateTestRunFailuresWithoutInfiniteLoop;
			this.useExceptionForSimulationFailuresWithoutInfiniteLoop = useExceptionForSimulationFailuresWithoutInfiniteLoop;
			this.testCaseIDsWithInfiniteLoop = testCaseIDsWithInfiniteLoop;
			this.numberOfTestCasesExecuted = 0;
		}

		public int getErrorCount() {
			return this.simulateTestRunFailuresWithoutInfiniteLoop ? 1 : 0;
		}

		public void setMutant(Mutant mutant) {
		}

		public void setTestCase(String testCase) {
		}

		public void setTestClassesLocation(URL[] testClassesLocation) {
		}

		public void run() {

			this.finished = false;

			logger.debug("Testcase No. : " + numberOfTestCasesExecuted);

			logger.debug("Mock: Waiting for parent to allow starting the mutation process");
			synchronized (lock) {
			}

			logger.debug("Mock: Received permission to start mutation process");

			if (this.simulateTestRunFailuresWithoutInfiniteLoop) {
				logger.debug("Mock: Simulating test failure.");
				if (this.useExceptionForSimulationFailuresWithoutInfiniteLoop) {
					logger.debug("Mock: .... test failure with exception");
					finished = true;
					synchronized (lock) {
						lock.notify();
					}
				} else {
					finished = true;
					synchronized (lock) {
						lock.notify();
					}
					return;
				}
			} else {
				logger.debug("Simulating test run without any failures");
			}

			if (testCaseIDsWithInfiniteLoop.size() > numberOfTestCasesExecuted
					&& testCaseIDsWithInfiniteLoop.get(numberOfTestCasesExecuted).equals(Boolean.TRUE)) {

				numberOfTestCasesExecuted++;
				while (true) {
					logger.debug("Mock: I'm hanging !");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			} else {

				numberOfTestCasesExecuted++;
				synchronized (lock) {
					finished = true;
					logger.debug("Finished mutation process for a testcase");
					lock.notify();
				}
			}

		}

		public boolean finished() {
			return this.finished;
		}

		public void setFinishedSynchroLock(Object lock) {
			this.lock = lock;
		}

	}

	public void test_CalculateCombinedClassPath() throws MalformedURLException {
		JUnitRunner runner = new JUnitRunner();

		runner.setClassesLocations(new URL[] { new URL("file:classDir1"), new URL("file:classDir2") });
		runner.setTestClassesLocations(new URL[] { new URL("file:testclassDir1"), new URL("file:testclassDir2") });
		runner.setLibraries(new URL[] { new URL("file:libDir1"), new URL("file:libDir2") });

		URL[] urls = runner.calculateCombinedClassPath();

		assertEquals(6, urls.length);

		assertEquals("file:classDir1", urls[0].toExternalForm());
		assertEquals("file:classDir2", urls[1].toExternalForm());
		assertEquals("file:testclassDir1", urls[2].toExternalForm());
		assertEquals("file:testclassDir2", urls[3].toExternalForm());
		assertEquals("file:libDir1", urls[4].toExternalForm());
		assertEquals("file:libDir2", urls[5].toExternalForm());
	}

	public void test_CalculateCombinedClassPath_EmptyNoTestDirectories() throws MalformedURLException {
		JUnitRunner runner = new JUnitRunner();

		runner.setClassesLocations(new URL[] { new URL("file:classDir1"), new URL("file:classDir2") });
		runner.setLibraries(new URL[] { new URL("file:libDir1"), new URL("file:libDir2") });

		URL[] urls = runner.calculateCombinedClassPath();

		assertEquals(4, urls.length);

		assertEquals("file:classDir1", urls[0].toExternalForm());
		assertEquals("file:classDir2", urls[1].toExternalForm());
		assertEquals("file:libDir1", urls[2].toExternalForm());
		assertEquals("file:libDir2", urls[3].toExternalForm());
	}

	public void test_execute_ValidationStep_TestSetNotSane_WithoutException() {

		JUnitRunner runner = new JUnitRunner();

		runner.setRunner(new ThreadedMockMutationRunner(true, false));

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Set<String> brokenTests = runner.execute(testsToRun);

		assertTrue(brokenTests.contains("someTest1"));
		assertTrue(brokenTests.contains("someTest2"));

	}

	public void test_execute_ValidationStep_TestSetNotSane_WithExceptions() {

		JUnitRunner runner = new JUnitRunner();

		runner.setRunner(new ThreadedMockMutationRunner(true, true));

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Set<String> brokenTests = runner.execute(testsToRun);

		assertTrue(brokenTests.contains("someTest1"));
		assertTrue(brokenTests.contains("someTest2"));

	}

	public void test_execute_ValidationStep_TestSetSane() {

		JUnitRunner runner = new JUnitRunner();

		runner.setRunner(new ThreadedMockMutationRunner(false, false));

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Set<String> brokenTests = runner.execute(testsToRun);

		assertEquals(0, brokenTests.size());

	}

	public void test_execute_ExecutionStep_TestSetSane() {

		JUnitRunner runner = new JUnitRunner();

		runner.setRunner(new ThreadedMockMutationRunner(false, false));

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertEquals(2, mutant.getExecutedTestsNames().size());
		assertTrue(mutant.getExecutedTestsNames().contains("someTest1"));
		assertTrue(mutant.getExecutedTestsNames().contains("someTest2"));

		assertTrue(mutant.isSurvived());

	}

	public void test_execute_ExecutionStep_TestSetCausesExceptions() {

		// Testcase Not possible in real code as testset is validated previously
		// in the code

		JUnitRunner runner = new JUnitRunner();

		runner.setRunner(new ThreadedMockMutationRunner(true, true));
		runner.setStopOnFirstFailedTest(false);
		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertEquals(2, mutant.getExecutedTestsNames().size());
		assertTrue(mutant.getExecutedTestsNames().contains("someTest1"));
		assertTrue(mutant.getExecutedTestsNames().contains("someTest2"));

		assertFalse(mutant.isSurvived());

	}

	public void test_execute_ExecutionStep_Threaded_TestSetCausesFailures_StopOnFirstFailedTest() {

		JUnitRunner runner = new JUnitRunner();

		runner.setRunner(new ThreadedMockMutationRunner(true, false));
		runner.setStopOnFirstFailedTest(true);

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertEquals(1, mutant.getExecutedTestsNames().size());

		// Don't check which test was the first failing test. It's
		// not defined therefore it's sufficient to make sure
		// that only 1 test has been returned.

		assertFalse(mutant.isSurvived());
	}

	public void test_execute_ExecutionStep_Threaded_TestSetCausesFailures_DoNotStopOnFirstFailedTest() {

		JUnitRunner runner = new JUnitRunner();

		runner.setStopOnFirstFailedTest(false);

		runner.setRunner(new ThreadedMockMutationRunner(true, false));

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertEquals(2, mutant.getExecutedTestsNames().size());
		assertTrue(mutant.getExecutedTestsNames().contains("someTest1"));
		assertTrue(mutant.getExecutedTestsNames().contains("someTest2"));

		assertFalse(mutant.isSurvived());

	}

	public void test_execute_ExecutionStep_Threaded_TestSetSane_InfiniteLoopInFirstTestCaseDueToMutation() {

		JUnitRunner runner = new JUnitRunner();
		List<Boolean> testCasesWithInfiniteLoops = new ArrayList<Boolean>();
		testCasesWithInfiniteLoops.add(new Boolean(true));
		testCasesWithInfiniteLoops.add(new Boolean(false));

		runner.setRunner(new ThreadedMockMutationRunner(testCasesWithInfiniteLoops, false, false));
		runner.setTimeout(1000);
		
		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);
		assertFalse(mutant.isSurvived());
	}

	public void test_execute_ExecutionStep_Threaded_TestSetSane_InfiniteLoopInLastTestCaseDueToMutation() {

		JUnitRunner runner = new JUnitRunner();
		List<Boolean> testCasesWithInfiniteLoops = new ArrayList<Boolean>();
		testCasesWithInfiniteLoops.add(new Boolean(false));
		testCasesWithInfiniteLoops.add(new Boolean(true));

		runner.setRunner(new ThreadedMockMutationRunner(testCasesWithInfiniteLoops, false, false));
		runner.setTimeout(1000);
		
		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertFalse(mutant.isSurvived());
	}

	public void test_execute_ExecutionStep_Threaded_TestSetSane_NoInfiniteLoopInTestCasesDueToMutation() {

		JUnitRunner runner = new JUnitRunner();
		List<Boolean> testCasesWithInfiniteLoops = new ArrayList<Boolean>();
		testCasesWithInfiniteLoops.add(new Boolean(false));
		testCasesWithInfiniteLoops.add(new Boolean(false));

		runner.setRunner(new ThreadedMockMutationRunner(testCasesWithInfiniteLoops, false, false));
		runner.setTimeout(1000);
		
		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertTrue(mutant.isSurvived());
		assertEquals(2, mutant.getExecutedTestsNames().size());
	}

	public void test_execute_ExecutionStep_Threaded_TestSetSane_InfiniteLoopsInAllTestCasesDueToMutation() {

		JUnitRunner runner = new JUnitRunner();
		List<Boolean> testCasesWithInfiniteLoops = new ArrayList<Boolean>();
		testCasesWithInfiniteLoops.add(new Boolean(true));
		testCasesWithInfiniteLoops.add(new Boolean(true));

		runner.setRunner(new ThreadedMockMutationRunner(testCasesWithInfiniteLoops, false, false));
		runner.setTimeout(1000);

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertFalse(mutant.isSurvived());
	}

}
