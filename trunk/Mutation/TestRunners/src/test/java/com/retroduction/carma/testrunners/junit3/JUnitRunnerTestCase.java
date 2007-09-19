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

	private class MockMutationRunner implements IMutantJUnitRunner {

		boolean simulateTestRunFailures;

		boolean useExceptionForFailures;

		public MockMutationRunner(boolean simulateTestRunFailures, boolean useExceptionForFailures) {
			super();
			this.simulateTestRunFailures = simulateTestRunFailures;
			this.useExceptionForFailures = useExceptionForFailures;
		}

		public int perform(String testCase, URL[] testClassesLocation, Mutant mutant) {
			if (this.simulateTestRunFailures) {
				if (this.useExceptionForFailures) {
					throw new RuntimeException("MockException");
				} else {
					return 1;
				}
			} else {
				return 0;
			}
		}

		public int getErrorCount() {
			throw new UnsupportedOperationException("Not implemented by mock");
		}

		public void setMutant(Mutant mutant) {
			throw new UnsupportedOperationException("Not implemented by mock");
		}

		public void setTestCase(String testCase) {
			throw new UnsupportedOperationException("Not implemented by mock");
		}

		public void setTestClassesLocation(URL[] testClassesLocation) {
			throw new UnsupportedOperationException("Not implemented by mock");
		}

		public void run() {
			throw new UnsupportedOperationException("Not implemented by mock");
		}

		public boolean finished() {
			throw new UnsupportedOperationException("Not implemented by mock");
		}

		public void setFinishedSynchroLock(Object lock) {
			throw new UnsupportedOperationException("Not implemented by mock");
		}

	}

	private class ThreadedMockMutationRunner implements IMutantJUnitRunner {

		private List<Boolean> testCaseIDsWithInfiniteLoop;

		private int numberOfTestCasesExecuted;

		private Object lock;

		private boolean finished;

		public ThreadedMockMutationRunner(List<Boolean> testCaseIDsWithInfiniteLoop) {
			super();
			this.testCaseIDsWithInfiniteLoop = testCaseIDsWithInfiniteLoop;
			this.numberOfTestCasesExecuted = 0;
		}

		public int perform(String testCase, URL[] testClassesLocation, Mutant mutant) {
			throw new UnsupportedOperationException("Not implemented by mock");
		}

		public int getErrorCount() {
			return 0;
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

			if (testCaseIDsWithInfiniteLoop.get(numberOfTestCasesExecuted).equals(Boolean.TRUE)) {

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

		runner.setRunner(new MockMutationRunner(true, false));

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Set<String> brokenTests = runner.execute(testsToRun);

		assertTrue(brokenTests.contains("someTest1"));
		assertTrue(brokenTests.contains("someTest2"));

	}

	public void test_execute_ValidationStep_TestSetNotSane_WithExceptions() {

		JUnitRunner runner = new JUnitRunner();

		runner.setRunner(new MockMutationRunner(true, true));

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Set<String> brokenTests = runner.execute(testsToRun);

		assertTrue(brokenTests.contains("someTest1"));
		assertTrue(brokenTests.contains("someTest2"));

	}

	public void test_execute_ValidationStep_TestSetSane() {

		JUnitRunner runner = new JUnitRunner();

		runner.setRunner(new MockMutationRunner(false, false));

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Set<String> brokenTests = runner.execute(testsToRun);

		assertEquals(0, brokenTests.size());

	}

	public void test_execute_ExecutionStep_TestSetSane() {

		JUnitRunner runner = new JUnitRunner();

		runner.setRunner(new MockMutationRunner(false, false));

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertEquals(2, mutant.getExecutedTestsNames().size());
		assertTrue(mutant.getExecutedTestsNames().contains("someTest1"));
		assertTrue(mutant.getExecutedTestsNames().contains("someTest2"));

	}

	public void test_execute_ExecutionStep_TestSetCausesExceptions() {

		// Testcase Not possible in real code as testset is validated previously
		// in the code

		JUnitRunner runner = new JUnitRunner();

		runner.setRunner(new MockMutationRunner(true, true));

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertEquals(0, mutant.getExecutedTestsNames().size());

	}

	public void test_execute_ExecutionStep_TestSetCausesFailures_StopOnFirstFailedTest() {

		JUnitRunner runner = new JUnitRunner();

		runner.setRunner(new MockMutationRunner(true, false));

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertEquals(1, mutant.getExecutedTestsNames().size());

		// Don't check which test was the first failing test. It's
		// not defined therefore it's sufficient to make sure
		// that only 1 test has been returned.
	}

	public void test_execute_ExecutionStep_TestSetCausesFailures_DoNotStopOnFirstFailedTest() {

		JUnitRunner runner = new JUnitRunner();

		runner.setStopOnFirstFailedTest(false);

		runner.setRunner(new MockMutationRunner(true, false));

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertEquals(2, mutant.getExecutedTestsNames().size());
		assertTrue(mutant.getExecutedTestsNames().contains("someTest1"));
		assertTrue(mutant.getExecutedTestsNames().contains("someTest2"));

	}

	public void test_execute_ExecutionStep_TestSetSane_InfiniteLoopInFirstTestCaseDueToMutation() {

		JUnitRunner runner = new JUnitRunner();
		List<Boolean> testCasesWithInfiniteLoops = new ArrayList<Boolean>();
		testCasesWithInfiniteLoops.add(new Boolean(true));
		testCasesWithInfiniteLoops.add(new Boolean(false));

		runner.setRunner(new ThreadedMockMutationRunner(testCasesWithInfiniteLoops));

		runner.setThreadedModeActive(true);

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);
		assertFalse(mutant.isSurvived());
	}

	public void test_execute_ExecutionStep_TestSetSane_InfiniteLoopInLastTestCaseDueToMutation() {

		JUnitRunner runner = new JUnitRunner();
		List<Boolean> testCasesWithInfiniteLoops = new ArrayList<Boolean>();
		testCasesWithInfiniteLoops.add(new Boolean(false));
		testCasesWithInfiniteLoops.add(new Boolean(true));

		runner.setRunner(new ThreadedMockMutationRunner(testCasesWithInfiniteLoops));

		runner.setThreadedModeActive(true);

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertFalse(mutant.isSurvived());
	}

	public void test_execute_ExecutionStep_TestSetSane_NoInfiniteLoopInTestCasesDueToMutation() {

		JUnitRunner runner = new JUnitRunner();
		List<Boolean> testCasesWithInfiniteLoops = new ArrayList<Boolean>();
		testCasesWithInfiniteLoops.add(new Boolean(false));
		testCasesWithInfiniteLoops.add(new Boolean(false));

		runner.setRunner(new ThreadedMockMutationRunner(testCasesWithInfiniteLoops));

		runner.setThreadedModeActive(true);

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertTrue(mutant.isSurvived());
		assertEquals(2, mutant.getExecutedTestsNames().size());
	}

	public void test_execute_ExecutionStep_TestSetSane_InfiniteLoopsInAllTestCasesDueToMutation() {

		JUnitRunner runner = new JUnitRunner();
		List<Boolean> testCasesWithInfiniteLoops = new ArrayList<Boolean>();
		testCasesWithInfiniteLoops.add(new Boolean(true));
		testCasesWithInfiniteLoops.add(new Boolean(true));

		runner.setRunner(new ThreadedMockMutationRunner(testCasesWithInfiniteLoops));

		runner.setThreadedModeActive(true);

		Set<String> testsToRun = new HashSet<String>();

		testsToRun.add("someTest1");
		testsToRun.add("someTest2");

		Mutant mutant = new Mutant();

		runner.execute(mutant, testsToRun);

		assertFalse(mutant.isSurvived());
	}

}
