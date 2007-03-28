package com.mutation;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mutation.events.IEventListener;

public class Driver {

	private ITestRunner testRunner;

	private IEventListener eventListener;

	private IClassSetResolver classSetResolver;

	private ITestSetResolver testSetResolver;

	private IMutantGenerator mutantGenerator;

	public static void main(String[] args) throws MalformedURLException, FileNotFoundException {
		ApplicationContext factory = new FileSystemXmlApplicationContext("muntationconfig.xml");

		Set<EMutationOperator> operators = (Set<EMutationOperator>) factory.getBean("operators");;
		Driver driver = (Driver) factory.getBean("testDriver");
		driver.execute(operators);

	}	
	public void execute(Set<EMutationOperator> operators) {

		Set<String> classUnderTestNames = classSetResolver.determineClassNames(eventListener);

		for (String classUnderTestName : classUnderTestNames) {

			Set<String> testNames = testSetResolver.determineTests(classUnderTestName, eventListener);

			for (EMutationOperator operator : operators) {

				List<Mutant> mutants = mutantGenerator.generateMutants(classUnderTestName, operator, eventListener);

				for (Mutant mutant : mutants) {
					testRunner.execute(mutant, testNames, eventListener);
				}
			}
		}
	}
	public void setClassSetResolver(IClassSetResolver classSetResolver) {
		this.classSetResolver = classSetResolver;
	}
	public void setEventListener(IEventListener eventListener) {
		this.eventListener = eventListener;
	}
	public void setMutantGenerator(IMutantGenerator mutantGenerator) {
		this.mutantGenerator = mutantGenerator;
	}
	public void setTestRunner(ITestRunner testRunner) {
		this.testRunner = testRunner;
	}
	public void setTestSetResolver(ITestSetResolver testSetResolver) {
		this.testSetResolver = testSetResolver;
	}

}
