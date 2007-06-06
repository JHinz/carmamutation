package com.mutation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mutation.runner.ClassDescription;
import com.mutation.runner.MutationRunner;
import com.mutation.runner.events.ClassesUnderTestResolved;
import com.mutation.runner.events.IEventListener;
import com.mutation.runner.events.MutationProcessFinished;
import com.mutation.runner.events.MutationProcessStarted;
import com.mutation.runner.events.TestSetDetermined;
import com.mutation.transform.TransitionGroupConfig;
import com.mutation.util.CLIValidator;

public class BasicDriver {

	private static final String DEFAULT_USER_CONFIG = "config.xml";

	private final static String DEFAULT_GLUE_CONFIG = "mutationConfig.xml";

	private IEventListener eventListener;

	private IResolver resolver;

	private MutationRunner runner;

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
		BasicDriver driver = (BasicDriver) appContext.getBean("testDriver");
		driver.execute(tgConfig);
	}

	public void execute(TransitionGroupConfig tgConfig) {

		eventListener.notifyEvent(new MutationProcessStarted(tgConfig.getTransitionGroups()));

		List<ClassDescription> classesUnderTest = resolver.resolve();

		eventListener.notifyEvent(new ClassesUnderTestResolved(classesUnderTest));

		for (ClassDescription classUnderTestDescription : classesUnderTest) {

			eventListener.notifyEvent(new TestSetDetermined(classUnderTestDescription.getQualifiedClassName(),
					classUnderTestDescription.getAssociatedTestNames()));

		}

		try {
			runner.performMutations(tgConfig.getTransitionGroups(), classesUnderTest);
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

}
