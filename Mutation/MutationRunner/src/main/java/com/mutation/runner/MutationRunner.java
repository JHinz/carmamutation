package com.mutation.runner;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mutation.runner.IClassSetResolver.ClassDescription;
import com.mutation.runner.events.IEvent;
import com.mutation.runner.events.IEventListener;
import com.mutation.runner.events.MutantsGenerated;
import com.mutation.runner.events.ProcessingClassUnderTest;
import com.mutation.runner.events.ProcessingClassUnderTestFinished;
import com.mutation.runner.events.ProcessingMutant;
import com.mutation.runner.events.ProcessingMutationOperator;
import com.mutation.runner.utililties.ByteCodeFileReader;
import com.mutation.testrunner.ITestRunner;
import com.mutation.testrunner.JUnitRunner;
import com.mutation.transform.AbstractTransitionGroup;
import com.mutation.transform.asm.ror.ROR_TransitionGroup;

public class MutationRunner {

	private ITestRunner testRunner;

	private IMutantGenerator mutantGenerator;

	private File originalClassPath;

	private IEventListener eventListener;

	/**
	 * 
	 * Integration interface method for direct access using complex datatypes.
	 * 
	 * @param operators
	 * @param byteCodeFileReader
	 * @param classUnderTestDescription
	 * @param testNames
	 * @throws IOException
	 */
	public void performMutations(List<AbstractTransitionGroup> transitionGroups, ByteCodeFileReader byteCodeFileReader,
			ClassDescription classUnderTestDescription, Set<String> testNames) throws IOException {

		eventListener.notifyEvent(new ProcessingClassUnderTest(classUnderTestDescription));

		String fqClassName = classUnderTestDescription.getQualifiedClassName();

		byte[] byteCode = loadClass(byteCodeFileReader, fqClassName);

		for (AbstractTransitionGroup transitionGroup : transitionGroups) {

			eventListener.notifyEvent(new ProcessingMutationOperator(transitionGroup.getClass().getName()));

			List<Mutant> mutants = mutantGenerator.generateMutants(fqClassName, byteCode, transitionGroups,
					eventListener);

			eventListener.notifyEvent(new MutantsGenerated(mutants, fqClassName, transitionGroup));

			for (Mutant mutant : mutants) {

				mutant.getSourceMapping().setClassName(fqClassName);
				mutant.getSourceMapping().setSourceFile(classUnderTestDescription.getClassFile());

				eventListener.notifyEvent(new ProcessingMutant(mutant));

				testRunner.execute(mutant, testNames, eventListener);
			}
		}

		eventListener.notifyEvent(new ProcessingClassUnderTestFinished());

	}

	/**
	 * 
	 * Integration interface method for external access using simple datatypes.
	 * 
	 * @param operators
	 * @param classUnderTest
	 * @param classUnderTestFile
	 * @param testNames
	 * @throws IOException
	 */
	private void performMutations(List<String> operators, String classUnderTest, String classUnderTestFile,
			Set<String> testNames) throws IOException {

		this.eventListener = new IEventListener() {

			public void notifyEvent(IEvent event) {
			};
		};

		List<AbstractTransitionGroup> convertedOperators = new ArrayList<AbstractTransitionGroup>();
		convertedOperators.add(new ROR_TransitionGroup(true));

		ClassDescription classUnderTestDescription = new ClassDescription();
		classUnderTestDescription.setClassFile(classUnderTestFile);

		int lastDotIndex = classUnderTest.lastIndexOf(".");

		if (lastDotIndex > 0) {
			classUnderTestDescription.setPackageName(classUnderTest.substring(0, lastDotIndex - 1));
			classUnderTestDescription.setClassName(classUnderTest.substring(lastDotIndex));
		} else {
			classUnderTestDescription.setPackageName("");
			classUnderTestDescription.setClassName(classUnderTest);

		}

		performMutations(convertedOperators, new ByteCodeFileReader(), classUnderTestDescription, testNames);

	}

	/**
	 * Public main method for integration via creation of a new JVM (e.g. from
	 * eclipse).
	 * 
	 * @param argv
	 */
	public static void main(String[] argv) {

		JUnitRunner testRunner = new JUnitRunner();
		testRunner.setStopOnFirstFailedTest(false);
		try {
			testRunner.setTestClassesLocations(new URL[] { new URL(
					"file:/Users/raua/Documents/workspace/SampleProjectUnderTest/target/test-classes/") });
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return;
		}

		MutationRunner runner = new MutationRunner();
		runner.setTestRunner(testRunner);

		List<String> operators = new ArrayList<String>();
		operators.add("ROR");

		String classUnderTest = "com.mutation.test.Sample";

		String classUnderTestFile = "com/mutation/test/Sample.class";

		Set<String> testNames = new HashSet<String>();
		testNames.add("com.mutation.test.SampleTestCase");

		runner.setOriginalClassPath(new File("../../../SampleProjectUnderTest/target/classes"));

		try {
			runner.performMutations(operators, classUnderTest, classUnderTestFile, testNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private byte[] loadClass(ByteCodeFileReader byteCodeFileReader, String classUnderTestName) throws IOException {

		String path = originalClassPath.getAbsolutePath() + "/" + classUnderTestName.replace('.', '/') + ".class";

		File originalClassFile = new File(path);

		return byteCodeFileReader.readByteCodeFromDisk(originalClassFile);
	}

	public void setMutantGenerator(IMutantGenerator mutantGenerator) {
		this.mutantGenerator = mutantGenerator;
	}

	public void setTestRunner(ITestRunner testRunner) {
		this.testRunner = testRunner;
	}

	public File getOriginalClassPath() {
		return originalClassPath;
	}

	public void setOriginalClassPath(File originalClassPath) {
		this.originalClassPath = originalClassPath;
	}

	public void setEventListener(IEventListener eventListener) {
		this.eventListener = eventListener;
	}

}
