package com.mutation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mutation.events.DriverFinished;
import com.mutation.events.DriverStarted;
import com.mutation.events.IEventListener;
import com.mutation.util.ByteCodeFileReader;

public class Driver {

	private ITestRunner testRunner;

	private IEventListener eventListener;

	private IClassSetResolver classSetResolver;

	private ITestSetResolver testSetResolver;

	private IMutantGenerator mutantGenerator;

	private File originalClassPath;

	public static void main(String[] args) throws MalformedURLException, FileNotFoundException {
		ApplicationContext factory = new FileSystemXmlApplicationContext("mutationconfig.xml");

		List<EMutationOperator> operators = (List<EMutationOperator>) factory.getBean("operators");
		Driver driver = (Driver) factory.getBean("testDriver");
		driver.execute(operators);
	}

	public void execute(List<EMutationOperator> operators) {
		
		eventListener.notifyEvent(new DriverStarted(operators));
		Set<String> classUnderTestNames = classSetResolver.determineClassNames(eventListener);
		
		ByteCodeFileReader byteCodeFileReader = new ByteCodeFileReader();

		for (String classUnderTestName : classUnderTestNames) {

			String path = originalClassPath.getAbsolutePath() + "/" + classUnderTestName.replace('.', '/') + ".class";
			File originalClassFile = new File(path);

			byte[] byteCode = null;

			try {
				byteCode = byteCodeFileReader.readByteCodeFromDisk(originalClassFile);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}

			Set<String> testNames = testSetResolver.determineTests(classUnderTestName, eventListener);

			for (EMutationOperator operator : operators) {

				List<Mutant> mutants = mutantGenerator.generateMutants(classUnderTestName, byteCode, operator,
						eventListener);

				for (Mutant mutant : mutants) {
					testRunner.execute(mutant, testNames, eventListener);
				}
			}
		}
		eventListener.notifyEvent(new DriverFinished());
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

	public File getOriginalClassPath() {
		return originalClassPath;
	}

	public void setOriginalClassPath(File originalClassPath) {
		this.originalClassPath = originalClassPath;
	}

}
