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

import com.retroduction.carma.application.util.CLIValidator;
import com.retroduction.carma.core.ICoreConfigConsts;
import com.retroduction.carma.core.MutationRunner;
import com.retroduction.carma.core.api.events.IEventListener;
import com.retroduction.carma.core.api.events.MutationProcessFinished;
import com.retroduction.carma.core.api.events.MutationProcessStarted;
import com.retroduction.carma.core.api.resolvers.IResolver;
import com.retroduction.carma.core.api.testrunners.ClassDescription;
import com.retroduction.carma.core.api.transitions.TransitionGroupConfig;

public class Carma {

	private static final String DEFAULT_USER_CONFIG = "config.xml";

	private IEventListener eventListener;

	private IResolver resolver;

	private MutationRunner runner;

	private TransitionGroupConfig transitionGroupConfig;

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

		springResources.add(ICoreConfigConsts.EVENTLISTENER_CONFIG_FILE);
		springResources.add(ICoreConfigConsts.RUNNER_CONFIG_FILE);
		springResources.add(ICoreConfigConsts.TRANSITIONS_CONFIG_FILE);
		springResources.add(ICarmaConfigConsts.CARMA_APPLICATION_CONFIG_FILE);

		AbstractXmlApplicationContext appContext = new ClassPathXmlApplicationContext(springResources
				.toArray(new String[0]));

		appContext.registerShutdownHook();

		Carma driver = (Carma) appContext.getBean(ICarmaConfigConsts.BEAN_CARMA);
		driver.execute();
	}

	public void execute() {

		eventListener.notifyEvent(new MutationProcessStarted(getTransitionGroupConfig().getTransitionGroups()));

		List<ClassDescription> classesUnderTest = resolver.resolve();

		Set<ClassDescription> classesWithWorkingTestSet = new HashSet<ClassDescription>();

		for (ClassDescription classUnderTestDescription : classesUnderTest) {
			if (runner.performTestsetVerification(classUnderTestDescription.getAssociatedTestNames())) {
				classesWithWorkingTestSet.add(classUnderTestDescription);
			}
		}

		try {// expect set instead of list below !
			runner.performMutations(getTransitionGroupConfig().getTransitionGroups(), new ArrayList<ClassDescription>(
					classesWithWorkingTestSet));
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

	public IResolver getResolver() {
		return resolver;
	}

	public void setResolver(IResolver resolver) {
		this.resolver = resolver;
	}

	public TransitionGroupConfig getTransitionGroupConfig() {
		return transitionGroupConfig;
	}

	public void setTransitionGroupConfig(TransitionGroupConfig transitionGroupConfig) {
		this.transitionGroupConfig = transitionGroupConfig;
	}

}
