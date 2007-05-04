package com.mutation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mutation.runner.EMutationOperator;
import com.mutation.runner.IClassSetResolver;
import com.mutation.runner.ITestSetResolver;
import com.mutation.runner.MutationRunner;
import com.mutation.runner.IClassSetResolver.ClassDescription;
import com.mutation.runner.events.ClassesUnderTestResolved;
import com.mutation.runner.events.DriverFinished;
import com.mutation.runner.events.DriverStarted;
import com.mutation.runner.events.IEventListener;
import com.mutation.runner.events.TestSetDetermined;
import com.mutation.runner.utililties.ByteCodeFileReader;

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

		List<EMutationOperator> operators = (List<EMutationOperator>) factory.getBean("operators");
		BasicDriver driver = (BasicDriver) factory.getBean("testDriver");
		driver.execute(operators);
	}

	public void execute(List<EMutationOperator> operators) {

		eventListener.notifyEvent(new DriverStarted(operators));
		Set<IClassSetResolver.ClassDescription> classUnderTestNames = classSetResolver.determineClassNames();
		eventListener.notifyEvent(new ClassesUnderTestResolved(classUnderTestNames));

		ByteCodeFileReader byteCodeFileReader = new ByteCodeFileReader();

		for (ClassDescription classUnderTestDescription : classUnderTestNames) {

			try {

				Set<String> testNames = testSetResolver.determineTests(classUnderTestDescription.getQualifiedClassName());
				eventListener.notifyEvent( new TestSetDetermined(classUnderTestDescription.getQualifiedClassName(), testNames));
				runner.performMutations(operators, byteCodeFileReader, classUnderTestDescription, testNames);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		eventListener.notifyEvent(new DriverFinished());

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
