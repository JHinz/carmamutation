package com.retroduction.carma.core.testrunner.junit3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.runner.BaseTestRunner;
import junit.runner.TestSuiteLoader;

import com.retroduction.carma.core.runner.Mutant;
import com.retroduction.carma.core.testrunner.MutationClassLoader;

/**
 * JUnit Runner for mutation tests. Uses specific class loeader to load thze mutants
 * @author mike
 *
 */
public class MutantJUnitRunner extends BaseTestRunner {
	MyTestSuiteLoader loader;

	@Override
	public TestSuiteLoader getLoader() {
		return loader;
	}

	private ClassLoader mutantLoader;

	public MutantJUnitRunner(URL[] testClassesLocation, Mutant mutant) {

//		ClassLoaderInfo.printLoader(getClass());
		mutantLoader = new MutationClassLoader(testClassesLocation, mutant
				.getClassName(), mutant.getByteCode(), getClass().getClassLoader());
		loader = new MyTestSuiteLoader();
		Thread.currentThread().setContextClassLoader(mutantLoader);
	}
	
	/**
	 * Returns the Test corresponding to the given suite. This is
	 * a template method, subclasses override runFailed(), clearStatus().
	 */
	public Test getTest(String suiteClassName) {
		if (suiteClassName.length() <= 0) {
			clearStatus();
			return null;
		}
		Class testClass= null;
		try {
			testClass= loadSuiteClass(suiteClassName);
		} catch (ClassNotFoundException e) {
			String clazz= e.getMessage();
			if (clazz == null)
				clazz= suiteClassName;
			runFailed("Class not found \""+clazz+"\"");
			return null;
		} catch(Exception e) {
			runFailed("Error: "+e.toString());
			return null;
		}
		Method suiteMethod= null;
		try {
			suiteMethod= testClass.getMethod(SUITE_METHODNAME, new Class[0]);
	 	} catch(Exception e) {
	 		// try to extract a test suite automatically
			clearStatus();
			return new TestSuite(testClass);
		}
		if (! Modifier.isStatic(suiteMethod.getModifiers())) {
			runFailed("Suite() method must be static");
			return null;
		}
		Test test= null;
		try {
			Object testO = suiteMethod.invoke(null, (Object[])new Class[0]); // static method
			test= (Test)testO;
			if (test == null)
				return test;
		}
		catch (InvocationTargetException e) {
			runFailed("Failed to invoke suite():" + e.getTargetException().toString());
			return null;
		}
		catch (IllegalAccessException e) {
			runFailed("Failed to invoke suite():" + e.toString());
			return null;
		}

		clearStatus();
		return test;
	}	

	class MyTestSuiteLoader implements TestSuiteLoader {
		public Class load(String suiteClassName) throws ClassNotFoundException {
			return mutantLoader.loadClass(suiteClassName);
		}

		public Class reload(Class aClass) throws ClassNotFoundException {
			throw new UnsupportedOperationException("Not implemented");
		}
	}

	
	public TestResult doRun(Test suite, boolean wait) {
		TestResult result= createTestResult();
		suite.run(result);
		return result;
	}
	/**
	 * Creates the TestResult to be used for the test run.
	 */
	protected TestResult createTestResult() {
		return new TestResult();
	}

	@Override
	protected void runFailed(String message) {
		throw new RuntimeException(message);
	}


	@Override
	public void testEnded(String testName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void testFailed(int status, Test test, Throwable t) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void testStarted(String testName) {
		// TODO Auto-generated method stub
		
	}	
	}
