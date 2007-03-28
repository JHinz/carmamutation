package mut.executer;

import java.net.URL;

import com.mutation.Mutant;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.runner.TestSuiteLoader;
import junit.textui.TestRunner;

/**
 * JUnit Runner for mutation tests. Uses specific class loeader to load thze mutants
 * @author mike
 *
 */
public class MutantJUnitRunner extends TestRunner {
	MyTestSuiteLoader loader;

	@Override
	public TestSuiteLoader getLoader() {
		return loader;
	}

	private ClassLoader mutantLoader;

	public MutantJUnitRunner(URL[] testClassesLocation, Mutant mutant) {

		mutantLoader = new MutationClassLoader(testClassesLocation, mutant
				.getClassName(), mutant.getByteCode());
		loader = new MyTestSuiteLoader();
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

}
