package com.mutation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mutation.IClassSetResolver.ClassDescription;
import com.mutation.events.ClassUnderTestNotFound;
import com.mutation.events.DriverFinished;
import com.mutation.events.DriverStarted;
import com.mutation.events.IEventListener;
import com.mutation.events.ProcessingClassUnderTest;
import com.mutation.events.ProcessingClassUnderTestFinished;
import com.mutation.events.ProcessingMutant;
import com.mutation.events.ProcessingMutationOperator;
import com.mutation.util.ByteCodeFileReader;

public class Driver {

	private ITestRunner testRunner;

	private IEventListener eventListener;

	private IClassSetResolver classSetResolver;

	private ITestSetResolver testSetResolver;

	private IMutantGenerator mutantGenerator;

	private File originalClassPath;

	public static void main(String[] args) throws MalformedURLException, FileNotFoundException {
		AbstractApplicationContext factory = new FileSystemXmlApplicationContext("mutationconfig.xml");
		factory.registerShutdownHook();

		List<EMutationOperator> operators = (List<EMutationOperator>) factory.getBean("operators");
		Driver driver = (Driver) factory.getBean("testDriver");
		driver.execute(operators);
	}

	public void execute(List<EMutationOperator> operators) {

		eventListener.notifyEvent(new DriverStarted(operators));
		Set<ClassDescription> classUnderTestNames = classSetResolver.determineClassNames(eventListener);

		ByteCodeFileReader byteCodeFileReader = new ByteCodeFileReader();

		for (ClassDescription classUnderTestDescription : classUnderTestNames) {

			eventListener.notifyEvent(new ProcessingClassUnderTest(classUnderTestDescription));

			try {

				byte[] byteCode = loadClass(byteCodeFileReader, classUnderTestDescription.getClassName());

				Set<String> testNames = testSetResolver.determineTests(classUnderTestDescription.getClassName(),
						eventListener);

				for (EMutationOperator operator : operators) {

					eventListener.notifyEvent(new ProcessingMutationOperator(operator.name()));

					List<Mutant> mutants = mutantGenerator.generateMutants(classUnderTestDescription.className,
							byteCode, operator, eventListener);

					for (Mutant mutant : mutants) {

						eventListener.notifyEvent(new ProcessingMutant(mutant));

						testRunner.execute(mutant, testNames, eventListener);
					}
				}

			} catch (IOException e) {
				eventListener.notifyEvent(new ClassUnderTestNotFound(classUnderTestDescription.className));
				e.printStackTrace();
			}

			eventListener.notifyEvent(new ProcessingClassUnderTestFinished());

		}
		eventListener.notifyEvent(new DriverFinished());
	}

	private byte[] loadClass(ByteCodeFileReader byteCodeFileReader, String classUnderTestName) throws IOException {

		String path = originalClassPath.getAbsolutePath() + "/" + classUnderTestName.replace('.', '/') + ".class";

		File originalClassFile = new File(path);

		return byteCodeFileReader.readByteCodeFromDisk(originalClassFile);
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
