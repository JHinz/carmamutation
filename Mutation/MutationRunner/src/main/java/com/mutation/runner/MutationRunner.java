package com.mutation.runner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.mutation.runner.events.IEventListener;
import com.mutation.runner.events.MutantsGenerated;
import com.mutation.runner.events.ProcessingClassUnderTest;
import com.mutation.runner.events.ProcessingClassUnderTestFinished;
import com.mutation.runner.events.ProcessingMutant;
import com.mutation.runner.events.ProcessingMutationOperator;
import com.mutation.runner.utililties.ByteCodeFileReader;
import com.mutation.testrunner.ITestRunner;
import com.mutation.transform.AbstractTransitionGroup;
import com.mutation.transform.IMutationGenerator;
import com.mutation.transform.MutantGenerator;

public class MutationRunner {

	private ITestRunner testRunner;

	private IMutationGenerator mutantGenerator;

	private File classesUnderTestPath;

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
	public void performMutations(List<AbstractTransitionGroup> transitionGroups,
			List<ClassDescription> classUnderTestDescriptions) throws IOException {

		for (ClassDescription classUnderTestDescription : classUnderTestDescriptions) {

			eventListener.notifyEvent(new ProcessingClassUnderTest(classUnderTestDescription));

			String fqClassName = classUnderTestDescription.getQualifiedClassName();

			byte[] byteCode = loadClass(fqClassName);

			for (AbstractTransitionGroup transitionGroup : transitionGroups) {

				eventListener.notifyEvent(new ProcessingMutationOperator(transitionGroup.getClass().getName()));

				List<Mutant> mutants = mutantGenerator.generateMutants(fqClassName, byteCode, transitionGroups,
						eventListener);

				System.out.println("Number of created mutants for current class: " + mutants.size());

				eventListener.notifyEvent(new MutantsGenerated(mutants, fqClassName, transitionGroup));

				for (Mutant mutant : mutants) {

					mutant.getSourceMapping().setClassName(fqClassName);

					eventListener.notifyEvent(new ProcessingMutant(mutant));

					testRunner.execute(mutant, classUnderTestDescription.getAssociatedTestNames(), eventListener);
				}
			}

			eventListener.notifyEvent(new ProcessingClassUnderTestFinished());

		}

	}

	private byte[] loadClass(String classUnderTestName) throws IOException {

		String path = classesUnderTestPath.getAbsolutePath() + "/" + classUnderTestName.replace('.', '/') + ".class";

		File originalClassFile = new File(path);

		return new ByteCodeFileReader().readByteCodeFromDisk(originalClassFile);
	}

	public void setMutantGenerator(IMutationGenerator mutantGenerator) {
		this.mutantGenerator = mutantGenerator;
	}

	public void setTestRunner(ITestRunner testRunner) {
		this.testRunner = testRunner;
	}

	public File getClassesUnderTestPath() {
		return classesUnderTestPath;
	}

	public void setClassesUnderTestPath(File classesUnderTestPath) {
		this.classesUnderTestPath = classesUnderTestPath;
	}

	public void setEventListener(IEventListener eventListener) {
		this.eventListener = eventListener;
	}

}
