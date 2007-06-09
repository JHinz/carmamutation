package com.retroduction.carma.core.runner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.core.runner.events.IEventListener;
import com.retroduction.carma.core.runner.events.MutantsGenerated;
import com.retroduction.carma.core.runner.events.ProcessingClassUnderTest;
import com.retroduction.carma.core.runner.events.ProcessingClassUnderTestFinished;
import com.retroduction.carma.core.runner.events.ProcessingMutant;
import com.retroduction.carma.core.runner.events.ProcessingMutationOperator;
import com.retroduction.carma.core.runner.utililties.ByteCodeFileReader;
import com.retroduction.carma.core.testrunner.ITestRunner;
import com.retroduction.carma.core.transform.AbstractTransitionGroup;
import com.retroduction.carma.core.transform.IMutationGenerator;

public class MutationRunner {

	private ITestRunner testRunner;

	private IMutationGenerator mutantGenerator;

	private File classesUnderTestPath;

	private IEventListener eventListener;
	
	private Log log = LogFactory.getLog(MutationRunner.class);

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
		
		log.info("Performing mutation on all classes");
				
		for (ClassDescription classUnderTestDescription : classUnderTestDescriptions) {
			
			log.info("Performing mutation on class: " + classUnderTestDescription.getQualifiedClassName());

			eventListener.notifyEvent(new ProcessingClassUnderTest(classUnderTestDescription));

			String fqClassName = classUnderTestDescription.getQualifiedClassName();

			byte[] byteCode = loadClass(fqClassName);

			for (AbstractTransitionGroup transitionGroup : transitionGroups) {
				
				log.info("Using transition group <"+transitionGroup.getName()+"> for mutation process");

				eventListener.notifyEvent(new ProcessingMutationOperator(transitionGroup.getClass().getName()));

				List<Mutant> mutants = mutantGenerator.generateMutants(fqClassName, byteCode, transitionGroups,
						eventListener);

				log.info("Number of created mutants for current class: " + mutants.size());

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
	public boolean performTestsetVerification(Set<String> testDescriptions) {

		log.info("Performing verification run for test set sanity");

		Set<String> brokenTestNames = testRunner.execute(testDescriptions);

		if (brokenTestNames.size() > 0) {
			log
					.error("Testset not sane. There are test failures without mutations");
			return false;
		} else {
			log.info("Testset is sane. No broken tests without mutation");
			return true;
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
