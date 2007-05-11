package com.mutation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mutation.runner.IClassSetResolver;
import com.mutation.runner.ITestSetResolver;
import com.mutation.runner.MutationRunner;
import com.mutation.runner.IClassSetResolver.ClassDescription;
import com.mutation.runner.events.ClassesUnderTestResolved;
import com.mutation.runner.events.IEventListener;
import com.mutation.runner.events.MutationProcessFinished;
import com.mutation.runner.events.MutationProcessStarted;
import com.mutation.runner.events.TestSetDetermined;
import com.mutation.runner.utililties.ByteCodeFileReader;
import com.mutation.transform.TransitionGroupConfig;

public class BasicDriver {
	private IEventListener eventListener;

	private IClassSetResolver classSetResolver;

	private ITestSetResolver testSetResolver;

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
		Set<IClassSetResolver.ClassDescription> classUnderTestNames = classSetResolver.determineClassNames();
		eventListener.notifyEvent(new ClassesUnderTestResolved(classUnderTestNames));

		ByteCodeFileReader byteCodeFileReader = new ByteCodeFileReader();

		for (ClassDescription classUnderTestDescription : classUnderTestNames) {

			try {

				Set<String> testNames = testSetResolver.determineTests(classUnderTestDescription
						.getQualifiedClassName());
				eventListener.notifyEvent(new TestSetDetermined(classUnderTestDescription.getQualifiedClassName(),
						testNames));
				runner.performMutations(tgConfig.getTransitionGroups(), byteCodeFileReader, classUnderTestDescription,
						testNames);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		eventListener.notifyEvent(new MutationProcessFinished());

	}

	public void setClassSetResolver(IClassSetResolver classSetResolver) {
		this.classSetResolver = classSetResolver;
	}

	public void setTestSetResolver(ITestSetResolver testSetResolver) {
		this.testSetResolver = testSetResolver;
	}

	public MutationRunner getRunner() {
		return runner;
	}

	public void setRunner(MutationRunner runner) {
		this.runner = runner;
	}

	public IClassSetResolver getClassSetResolver() {
		return classSetResolver;
	}

	public ITestSetResolver getTestSetResolver() {
		return testSetResolver;
	}

	public void setEventListener(IEventListener eventListener) {
		this.eventListener = eventListener;
	}

}
