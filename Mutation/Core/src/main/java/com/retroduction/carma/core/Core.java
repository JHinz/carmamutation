package com.retroduction.carma.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.retroduction.carma.core.api.eventlisteners.IEventListener;
import com.retroduction.carma.core.api.eventlisteners.om.ClassesUnderTestResolved;
import com.retroduction.carma.core.api.eventlisteners.om.MutantsGenerated;
import com.retroduction.carma.core.api.eventlisteners.om.MutationProcessFinished;
import com.retroduction.carma.core.api.eventlisteners.om.MutationProcessStarted;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingClassUnderTest;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingClassUnderTestFinished;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingMutant;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingMutationOperator;
import com.retroduction.carma.core.api.eventlisteners.om.TestSetDetermined;
import com.retroduction.carma.core.api.eventlisteners.om.TestSetNotSane;
import com.retroduction.carma.core.api.eventlisteners.om.TestsExecuted;
import com.retroduction.carma.core.api.resolvers.IResolver;
import com.retroduction.carma.core.api.testrunners.ITestRunner;
import com.retroduction.carma.core.api.testrunners.om.ClassDescription;
import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.transitions.IMutationGenerator;
import com.retroduction.carma.core.api.transitions.ITransitionGroup;
import com.retroduction.carma.core.api.transitions.om.TransitionGroupConfig;
import com.retroduction.carma.utilities.IByteCodeFileReader;
import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

public class Core {

	private Logger logger = LoggerFactory.getLogger(Core.class);

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

		logger.info("Investigating resources of target project");

		eventListener.notifyEvent(new MutationProcessStarted(transitionGroupConfig.getTransitionGroups()));

		logger.debug("Resolving valid classes under test.");

		Set<ClassDescription> foundClassesUnderTest = resolver.resolve();

		Set<ClassDescription> filteredClassesUnderTest = resolver.removeSuperfluousClassNames(foundClassesUnderTest);

		logger.info("Resolved " + filteredClassesUnderTest.size() + " valid classes under test.");

		eventListener
				.notifyEvent(new ClassesUnderTestResolved(new ArrayList<ClassDescription>(filteredClassesUnderTest)));

		logger.debug("Removing invalid or broken (unsuccessful) tests from test set");

		Set<ClassDescription> runnableTestClasses = resolver.removeSuperfluousTestClasses(filteredClassesUnderTest);

		logger.info("Performing verification run for test set sanity");

		Set<String> fullTestClassSet = new HashSet<String>();

		for (ClassDescription clazz : runnableTestClasses)
			fullTestClassSet.addAll(clazz.getAssociatedTestNames());

		Set<String> brokenTestNames = testRunner.execute(fullTestClassSet);

		checkForBrokenTests(runnableTestClasses, brokenTestNames);

		int nonDistinctTestClassCount = countTestCases(runnableTestClasses);

		logger.info("Resolved " + nonDistinctTestClassCount + " non distinct valid testclasses.");

		performMutations(transitionGroupConfig.getTransitionGroups(), runnableTestClasses);

		eventListener.notifyEvent(new MutationProcessFinished());

		eventListener.destroy();

	}

	private void checkForBrokenTests(Set<ClassDescription> classDescriptions, Set<String> brokenTestNames) {
		if (brokenTestNames.size() > 0) {
			logger.warn("Testset not sane. There are already test failures without mutation");
			eventListener.notifyEvent(new TestSetNotSane(brokenTestNames));

			StringBuffer brokenTestString = new StringBuffer();
			for (String brokenTest : brokenTestNames) {
				brokenTestString.append(brokenTest + " ");
			}
			logger.warn("Skipping defective tests. Proceeding with working ones. These are the broken ones: "
					+ brokenTestString);
		}

		for (ClassDescription clazz : classDescriptions) {
			for (String brokenTest : brokenTestNames) {
				if (clazz.getAssociatedTestNames().contains(brokenTest))
					clazz.getAssociatedTestNames().remove(brokenTest);
			}
		}

	}

	private int countTestCases(Set<ClassDescription> validTestClasses) {
		int nonDistinctTestClassCount = 0;
		for (ClassDescription clazz : validTestClasses) {
			eventListener.notifyEvent(new TestSetDetermined(clazz.getQualifiedClassName(), clazz
					.getAssociatedTestNames()));
			nonDistinctTestClassCount += clazz.getAssociatedTestNames().size();
		}
		return nonDistinctTestClassCount;
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
	void performMutations(Set<ITransitionGroup> transitionGroups, Set<ClassDescription> classUnderTestDescriptions) {

		logger.info("Performing mutation ...");

		for (ClassDescription classUnderTestDescription : classUnderTestDescriptions) {

			logger.info("Performing mutation on class: " + classUnderTestDescription.getQualifiedClassName());

			eventListener.notifyEvent(new ProcessingClassUnderTest(classUnderTestDescription));

			String fqClassName = classUnderTestDescription.getQualifiedClassName();
			
			logger.debug("Loading genuine class byte code for mutation process...");

			byte[] byteCode = null;
			try {
				byteCode = byteCodeFileReader.readByteCodeFromMultipleFolders(fqClassName, getClassesUnderTestPath());
			} catch (IOException e) {
				logger.warn("ByteCode for class could not be read from disk. Skipping class...");
				eventListener.notifyEvent(new ProcessingClassUnderTestFinished());
				continue;
			}

			for (ITransitionGroup transitionGroup : transitionGroups) {

				logger.debug("Using transition group <" + transitionGroup.getName() + "> for mutation process");

				eventListener.notifyEvent(new ProcessingMutationOperator(transitionGroup.getName()));

				List<Mutant> mutants = mutantGenerator.generateMutants(fqClassName, byteCode, transitionGroups);

				logger.info("Number of created mutants for current class: " + mutants.size());

				eventListener.notifyEvent(new MutantsGenerated(mutants, fqClassName, transitionGroup));

				for (Mutant mutant : mutants) {

					logger.debug("Processing mutant...");

					mutant.getSourceMapping().setClassName(fqClassName);

					eventListener.notifyEvent(new ProcessingMutant(mutant));
					
					logger.debug("Executing sane tests for created mutant...");

					testRunner.execute(mutant, classUnderTestDescription.getAssociatedTestNames());

					eventListener.notifyEvent(new TestsExecuted(mutant));

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
