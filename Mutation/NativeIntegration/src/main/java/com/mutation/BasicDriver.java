package com.mutation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mutation.runner.ClassDescription;
import com.mutation.runner.MutationRunner;
import com.mutation.runner.events.ClassesUnderTestResolved;
import com.mutation.runner.events.IEventListener;
import com.mutation.runner.events.MutationProcessFinished;
import com.mutation.runner.events.MutationProcessStarted;
import com.mutation.runner.events.TestSetDetermined;
import com.mutation.transform.TransitionGroupConfig;

public class BasicDriver {

	private IEventListener eventListener;

	private IResolver resolver;

	private MutationRunner runner;

	/**
	 * command line test runner, reads configuration from mutationconfig.xml
	 */
	public static void main(String[] args) throws MalformedURLException, FileNotFoundException {

		AbstractApplicationContext factory = new ClassPathXmlApplicationContext("mutationconfig.xml");

		factory.registerShutdownHook();

		TransitionGroupConfig tgConfig = (TransitionGroupConfig) factory.getBean("operators");
		BasicDriver driver = (BasicDriver) factory.getBean("testDriver");
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
