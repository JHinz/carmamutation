package com.mutation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mutation.IClassSetResolver.ClassDescription;
import com.mutation.util.ByteCodeFileReader;

public class BasicDriver {

	private IClassSetResolver classSetResolver;

	private ITestSetResolver testSetResolver;

	private MutationRunner runner;

	public static void main(String[] args) throws MalformedURLException, FileNotFoundException {

		AbstractApplicationContext factory = new ClassPathXmlApplicationContext("mutationconfig.xml");

		factory.registerShutdownHook();

		List<EMutationOperator> operators = (List<EMutationOperator>) factory.getBean("operators");
		BasicDriver driver = (BasicDriver) factory.getBean("testDriver");
		driver.execute(operators);
	}

	public void execute(List<EMutationOperator> operators) {

		Set<IClassSetResolver.ClassDescription> classUnderTestNames = classSetResolver.determineClassNames();

		ByteCodeFileReader byteCodeFileReader = new ByteCodeFileReader();

		for (ClassDescription classUnderTestDescription : classUnderTestNames) {

			try {

				Set<String> testNames = testSetResolver.determineTests(classUnderTestDescription.getClassName());

				runner.performMutations(operators, byteCodeFileReader, classUnderTestDescription, testNames);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
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

}
