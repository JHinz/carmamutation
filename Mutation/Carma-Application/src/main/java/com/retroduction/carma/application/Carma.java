package com.retroduction.carma.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.retroduction.carma.application.resolver.AbstractFilteredResolver;
import com.retroduction.carma.application.resolver.FilterVerifier;
import com.retroduction.carma.application.resolver.InstantiationVerifier;
import com.retroduction.carma.application.util.CLIValidator;
import com.retroduction.carma.core.runner.ClassDescription;
import com.retroduction.carma.core.runner.MutationRunner;
import com.retroduction.carma.core.runner.events.ClassesUnderTestResolved;
import com.retroduction.carma.core.runner.events.IEventListener;
import com.retroduction.carma.core.runner.events.MutationProcessFinished;
import com.retroduction.carma.core.runner.events.MutationProcessStarted;
import com.retroduction.carma.core.runner.events.TestSetDetermined;
import com.retroduction.carma.core.transform.TransitionGroupConfig;

public class Carma {

	private static final String DEFAULT_USER_CONFIG = "config.xml";

	private final static String DEFAULT_GLUE_CONFIG = "mutationConfig.xml";

	private IEventListener eventListener;

	private AbstractFilteredResolver resolver;

	private MutationRunner runner;

	private InstantiationVerifier instantiationVerifier;

	private FilterVerifier filterVerifier;

	/**
	 * command line test runner, reads configuration from mutationconfig.xml
	 * 
	 * @throws ParseException
	 */
	public static void main(String[] args) throws MalformedURLException, FileNotFoundException, ParseException {

		CommandLine line = new CLIValidator().readCLI(args);

		List<String> springResources = new ArrayList<String>();

		if (line.hasOption(CLIValidator.USER_CONFIG_OPTION_SHORT)) {
			springResources.add("file:" + line.getOptionValue(CLIValidator.USER_CONFIG_OPTION_SHORT));
		} else {
			springResources.add("file:" + DEFAULT_USER_CONFIG);
		}

		springResources.add(DEFAULT_GLUE_CONFIG);

		AbstractXmlApplicationContext appContext = new ClassPathXmlApplicationContext(springResources
				.toArray(new String[0]));

		appContext.registerShutdownHook();

		TransitionGroupConfig tgConfig = (TransitionGroupConfig) appContext.getBean("operators");
		Carma driver = (Carma) appContext.getBean("testDriver");
		driver.execute(tgConfig);
	}

	public void execute(TransitionGroupConfig tgConfig) {

		eventListener.notifyEvent(new MutationProcessStarted(tgConfig.getTransitionGroups()));

		List<ClassDescription> classesUnderTest = resolver.resolve();

		List<ClassDescription> filteredClassesUnderTest = instantiationVerifier
				.removeNonInstantiatableClasses(classesUnderTest);

		List<ClassDescription> filteredInstantiableClassesUnderTest = filterVerifier
				.removeExcludedClasses(filteredClassesUnderTest);

		List<ClassDescription> filteredInstantiableClassesUnderTest2 = filterVerifier
				.removeExcludedClasses(filteredInstantiableClassesUnderTest);

		eventListener.notifyEvent(new ClassesUnderTestResolved(filteredInstantiableClassesUnderTest2));

		Set<String> availableTests = new HashSet<String>();

		for (ClassDescription classUnderTestDescription : filteredInstantiableClassesUnderTest2) {

			eventListener.notifyEvent(new TestSetDetermined(classUnderTestDescription.getQualifiedClassName(),
					classUnderTestDescription.getAssociatedTestNames()));

			availableTests.addAll(classUnderTestDescription.getAssociatedTestNames());

		}

		if (!runner.performTestsetVerification(availableTests)) {
			this.eventListener.destroy();

			return;
		}

		try {
			runner.performMutations(tgConfig.getTransitionGroups(), filteredInstantiableClassesUnderTest2);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		eventListener.notifyEvent(new MutationProcessFinished());

		eventListener.destroy();

	}

	public MutationRunner getRunner() {
		return runner;
	}

	public void setRunner(MutationRunner runner) {
		this.runner = runner;
	}

	public void setEventListener(IEventListener eventListener) {
		this.eventListener = eventListener;
	}

	public AbstractFilteredResolver getResolver() {
		return resolver;
	}

	public void setResolver(AbstractFilteredResolver resolver) {
		this.resolver = resolver;
	}

	private FilterVerifier getFilterVerifier() {
		return filterVerifier;
	}

	public void setFilterVerifier(FilterVerifier filterVerifier) {
		this.filterVerifier = filterVerifier;
	}

	private InstantiationVerifier getInstantiationVerifier() {
		return instantiationVerifier;
	}

	public void setInstantiationVerifier(InstantiationVerifier verifier) {
		this.instantiationVerifier = verifier;
	}

}
