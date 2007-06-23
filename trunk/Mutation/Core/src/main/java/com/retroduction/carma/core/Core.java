package com.retroduction.carma.core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.core.api.eventlisteners.IEventListener;
import com.retroduction.carma.core.api.eventlisteners.om.MutantsGenerated;
import com.retroduction.carma.core.api.eventlisteners.om.MutationProcessFinished;
import com.retroduction.carma.core.api.eventlisteners.om.MutationProcessStarted;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingClassUnderTest;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingClassUnderTestFinished;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingMutant;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingMutationOperator;
import com.retroduction.carma.core.api.eventlisteners.om.TestSetNotSane;
import com.retroduction.carma.core.api.resolvers.IResolver;
import com.retroduction.carma.core.api.testrunners.ITestRunner;
import com.retroduction.carma.core.api.testrunners.om.ClassDescription;
import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.transitions.IMutationGenerator;
import com.retroduction.carma.core.api.transitions.ITransitionGroup;
import com.retroduction.carma.core.api.transitions.om.TransitionGroupConfig;
import com.retroduction.carma.utilities.IByteCodeFileReader;

public class Core {

	private Log log = LogFactory.getLog(Core.class);

	private ITestRunner testRunner;

	private IMutationGenerator mutantGenerator;

	private File[] classesUnderTestPath;

	private IEventListener eventListener;

	private TransitionGroupConfig transitionGroupConfig;

	private IResolver resolver;

	private IByteCodeFileReader byteCodeFileReader;

	public void setByteCodeFileReader(IByteCodeFileReader byteCodeFileReader) {
		this.byteCodeFileReader = byteCodeFileReader;
	}

	public void setTransitionGroupConfig(TransitionGroupConfig transitionGroupConfig) {
		this.transitionGroupConfig = transitionGroupConfig;
	}

	public void execute() {

		eventListener.notifyEvent(new MutationProcessStarted(transitionGroupConfig.getTransitionGroups()));

		Set<ClassDescription> classesUnderTest = resolver.resolve();

		try {// expect set instead of list below !
			performMutations(transitionGroupConfig.getTransitionGroups(), classesUnderTest);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		eventListener.notifyEvent(new MutationProcessFinished());

		eventListener.destroy();

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
	void performMutations(Set<ITransitionGroup> transitionGroups, Set<ClassDescription> classUnderTestDescriptions)
			throws IOException {

		log.info("Performing mutation on all classes");

		for (ClassDescription classUnderTestDescription : classUnderTestDescriptions) {

			log.info("Performing mutation on class: " + classUnderTestDescription.getQualifiedClassName());

			eventListener.notifyEvent(new ProcessingClassUnderTest(classUnderTestDescription));

			log.info("Performing verification run for test set sanity");

			Set<String> brokenTestNames = testRunner.execute(classUnderTestDescription.getAssociatedTestNames());

			if (brokenTestNames.size() > 0) {
				log.error("Testset not sane. There are test failures without mutations");
				eventListener.notifyEvent(new TestSetNotSane(brokenTestNames, classUnderTestDescription));
				for (String brokenTest : brokenTestNames) {
					log.error("Failing test: " + brokenTest);
				}
				eventListener.notifyEvent(new ProcessingClassUnderTestFinished());
				continue;

			}

			String fqClassName = classUnderTestDescription.getQualifiedClassName();

			byte[] byteCode = byteCodeFileReader
					.readByteCodeFromMultipleFolders(fqClassName, getClassesUnderTestPath());

			for (ITransitionGroup transitionGroup : transitionGroups) {

				log.info("Using transition group <" + transitionGroup.getName() + "> for mutation process");

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

	public void setMutantGenerator(IMutationGenerator mutantGenerator) {
		this.mutantGenerator = mutantGenerator;
	}

	public void setTestRunner(ITestRunner testRunner) {
		this.testRunner = testRunner;
	}

	public File[] getClassesUnderTestPath() {
		return classesUnderTestPath;
	}

	public void setClassesUnderTestPath(File[] classesUnderTestPath) {
		this.classesUnderTestPath = classesUnderTestPath;
	}

	public void setEventListener(IEventListener eventListener) {
		this.eventListener = eventListener;
	}

	public void setResolver(IResolver resolver) {
		this.resolver = resolver;
	}

}
