package com.retroduction.carma.core;

import java.io.File;
import java.io.IOException;
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
import com.retroduction.carma.core.api.eventlisteners.om.TestSetNotSane;
import com.retroduction.carma.core.api.eventlisteners.om.TestsExecuted;
import com.retroduction.carma.core.api.resolvers.IResolver;
import com.retroduction.carma.core.api.testrunners.ITestRunner;
import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.transitions.IMutationGenerator;
import com.retroduction.carma.core.api.transitions.ITransitionGroup;
import com.retroduction.carma.core.api.transitions.om.TransitionGroupConfig;
import com.retroduction.carma.core.om.PersistentClassInfo;
import com.retroduction.carma.core.om.TestedClassInfo;
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

		this.logger.info("Investigating resources of target project");

		this.eventListener.notifyEvent(new MutationProcessStarted(this.transitionGroupConfig.getTransitionGroups()));

		this.logger.debug("Resolving valid classes under test and associated test classes.");

		Set<TestedClassInfo> foundClassesUnderTest = this.resolver.resolve();

		this.eventListener.notifyEvent(new ClassesUnderTestResolved(foundClassesUnderTest));

		this.logger.info("Performing verification run for test set sanity");

		Set<String> fullTestClassSet = new HashSet<String>();

		for (TestedClassInfo clazz : foundClassesUnderTest)
			for (PersistentClassInfo testInfo : clazz.getAssociatedTestNames())
				fullTestClassSet.add(testInfo.getFullyQualifiedClassName());

		Set<String> brokenTestNames = this.testRunner.execute(fullTestClassSet);

		this.checkForBrokenTests(foundClassesUnderTest, brokenTestNames);

		this.logger.info("Resolved " + fullTestClassSet.size() + " non distinct valid testclasses.");

		this.performMutations(this.transitionGroupConfig.getTransitionGroups(), foundClassesUnderTest);

		this.eventListener.notifyEvent(new MutationProcessFinished());

		this.eventListener.destroy();

	}

	private void checkForBrokenTests(Set<TestedClassInfo> testedClassInfos, Set<String> brokenTestNames) {
		if (brokenTestNames.size() > 0) {
			this.logger.warn("Testset not sane. There are already test failures without mutation");
			this.eventListener.notifyEvent(new TestSetNotSane(brokenTestNames));

			StringBuffer brokenTestString = new StringBuffer();
			for (String brokenTest : brokenTestNames) {
				brokenTestString.append(brokenTest + " ");
			}
			this.logger.warn("Skipping defective tests. Proceeding with working ones. These are the broken ones: "
					+ brokenTestString);
		}

		for (TestedClassInfo clazz : testedClassInfos) {
			for (String brokenTest : brokenTestNames) {
				PersistentClassInfo testInfo = new PersistentClassInfo(brokenTest);
				if (clazz.getAssociatedTestNames().contains(testInfo))
					clazz.getAssociatedTestNames().remove(testInfo);

			}
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
	void performMutations(Set<ITransitionGroup> transitionGroups, Set<TestedClassInfo> classUnderTestDescriptions) {

		this.logger.info("Performing mutation ...");

		for (TestedClassInfo classUnderTestDescription : classUnderTestDescriptions) {

			this.logger.info("Performing mutation on class: " + classUnderTestDescription.getFullyQualifiedClassName());

			this.eventListener.notifyEvent(new ProcessingClassUnderTest(classUnderTestDescription));

			String fqClassName = classUnderTestDescription.getFullyQualifiedClassName();

			this.logger.debug("Loading genuine class byte code for mutation process...");

			byte[] byteCode = null;
			try {
				byteCode = this.byteCodeFileReader.readByteCodeFromMultipleFolders(fqClassName, this.getClassesUnderTestPath());
			} catch (IOException e) {
				this.logger.warn("ByteCode for class could not be read from disk. Skipping class " + fqClassName, e);
				this.eventListener.notifyEvent(new ProcessingClassUnderTestFinished());
				continue;
			}

			for (ITransitionGroup transitionGroup : transitionGroups) {

				this.logger.debug("Using transition group <" + transitionGroup.getName() + "> for mutation process");

				this.eventListener.notifyEvent(new ProcessingMutationOperator(transitionGroup.getName()));

				List<Mutant> mutants = this.mutantGenerator.generateMutants(fqClassName, byteCode, transitionGroups);

				this.logger.info("Number of created mutants for current class: " + mutants.size());

				this.eventListener.notifyEvent(new MutantsGenerated(mutants, fqClassName, transitionGroup));

				for (Mutant mutant : mutants) {

					this.logger.debug("Processing mutant...");

					mutant.getSourceMapping().setClassName(fqClassName);

					this.eventListener.notifyEvent(new ProcessingMutant(mutant));

					this.logger.debug("Executing sane tests for created mutant...");

					Set<String> associatedTestNames = new HashSet<String>();

					for (PersistentClassInfo testInfo : classUnderTestDescription.getAssociatedTestNames())
						associatedTestNames.add(testInfo.getFullyQualifiedClassName());

					this.testRunner.execute(mutant, associatedTestNames);

					this.eventListener.notifyEvent(new TestsExecuted(mutant));

				}
			}

			this.eventListener.notifyEvent(new ProcessingClassUnderTestFinished());

		}

	}

	public void setMutantGenerator(IMutationGenerator mutantGenerator) {
		this.mutantGenerator = mutantGenerator;
	}

	public void setTestRunner(ITestRunner testRunner) {
		this.testRunner = testRunner;
	}

	public File[] getClassesUnderTestPath() {
		return this.classesUnderTestPath;
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
