package com.retroduction.carma.testrunners.junit3;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.Mutant;

public class JUnitRunnerTestCase extends TestCase {

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
}
