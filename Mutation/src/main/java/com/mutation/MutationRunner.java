package com.mutation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mutation.IClassSetResolver.ClassDescription;
import com.mutation.events.IEventListener;
import com.mutation.events.ProcessingClassUnderTest;
import com.mutation.events.ProcessingClassUnderTestFinished;
import com.mutation.events.ProcessingMutant;
import com.mutation.events.ProcessingMutationOperator;
import com.mutation.util.ByteCodeFileReader;

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
	public void performMutations(List<EMutationOperator> operators, ByteCodeFileReader byteCodeFileReader,
			ClassDescription classUnderTestDescription, Set<String> testNames) throws IOException {

		eventListener.notifyEvent(new ProcessingClassUnderTest(classUnderTestDescription));

		byte[] byteCode = loadClass(byteCodeFileReader, classUnderTestDescription.getClassName());

		for (EMutationOperator operator : operators) {

			eventListener.notifyEvent(new ProcessingMutationOperator(operator.name()));

			List<Mutant> mutants = mutantGenerator.generateMutants(classUnderTestDescription.className, byteCode,
					operator, eventListener);

			for (Mutant mutant : mutants) {

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
	public void performMutations(List<String> operators, String classUnderTest, String classUnderTestFile,
			Set<String> testNames) throws IOException {

		List<EMutationOperator> convertedOperators = new ArrayList<EMutationOperator>();

		ClassDescription classUnderTestDescription = new ClassDescription();
		classUnderTestDescription.setClassFile(classUnderTestFile);
		classUnderTestDescription.setClassName(classUnderTest);

		performMutations(convertedOperators, new ByteCodeFileReader(), classUnderTestDescription, testNames);

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
