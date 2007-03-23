package mut.driver;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mut.IMutantGenerator;
import mut.IReportGenerator;
import mut.ITestExecuter;
import mut.Mutant;
import mut.MutationOperator;
import mut.MutationTestSpec;
import mut.executer.JUnitExecuter;
import mut.mutantgen.asm.ASMMutantCreator;
import mut.report.ConsoleReportGenerator;

/**
 * Drives complete mutation test for a provided test set
 * 
 * @author mike
 * 
 */
public class MutationTestDriver {
	private IMutantGenerator mutantGenerator;

	private ITestExecuter testExecuter;

	private IReportGenerator reportGenerator;

	static MutationTestDriver setUpDriver(String originalClassesLoc,
			String testClassesLoc) throws MalformedURLException {
		MutationTestDriver driver = new MutationTestDriver();

		{
			// - Initialize mutant generator
			// Location of the original classes under test
			File originalClassPath = new File(originalClassesLoc);
			ASMMutantCreator mutantGenerator = new ASMMutantCreator(
					originalClassPath);

			driver.setMutantGenerator(mutantGenerator);
		}
		{
			// - Initialize test executer

			// Location of the unit test classes
			URL origClassesUrl = new File(originalClassesLoc).toURL();
			URL testClassesUrl = new File(testClassesLoc).toURL();
			URL[] testClassesLocations = new URL[2];
			testClassesLocations[0] = origClassesUrl;
			testClassesLocations[1] = testClassesUrl;
			// File(args[0]).toURL();
			JUnitExecuter testExecuter = new JUnitExecuter();
			testExecuter.setTestClassesLocations(testClassesLocations);
			driver.setTestExecuter(testExecuter);
		}
		{
			// - Initialize report generator
			IReportGenerator reportGenerator = new ConsoleReportGenerator();
			driver.setReportGenerator(reportGenerator);
		}

		return driver;
	}

	/**
	 * @param args
	 * @throws MalformedURLException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws MalformedURLException,
			FileNotFoundException {

		// class name determined using Test Name - "Test" suffix
		if (args.length == 0) {
			System.out.println("Usage: java " + MutationTestDriver.class.getName()
					+ " unitTestClassName1 unitTestClassName2 ...");
			return;
		}

		for (int i = 0; i < args.length; i++) {
			String unitTestName = args[i]; // "org.apache.commons.codec.net.QCodecTest";
			String classUnderTest = unitTestName.substring(0, unitTestName
					.length()
					- "Test".length());

			String originalClassesLoc = System
					.getProperty("classes",
							"F:/workspaces/mutationTesting/commons-codec-1.3/target/classes");
			String testClassesLoc = System
					.getProperty("testclasses",
							"F:/workspaces/mutationTesting/commons-codec-1.3/target/testclasses");

			List<String> unitTestClasses = new ArrayList<String>();
			unitTestClasses.add(unitTestName);

			MutationTestDriver driver = setUpDriver(originalClassesLoc,
					testClassesLoc);

			{
				// Define test spec

				// Mutation operators to be applied
				Set<MutationOperator> operators = new HashSet<MutationOperator>();
				operators.add(MutationOperator.ROR);

				MutationTestSpec testSpec = new MutationTestSpec();
				testSpec.setClassUnderTest(classUnderTest);
				testSpec.setTestSet(unitTestClasses);
				testSpec.setOperators(operators);

				driver.execute(testSpec);
			}
		}

	}

	private void log(String msg) {
		if (true) {
			return;
		}
		System.out.println(new Date() + " " + getClass().getSimpleName()
				+ " ## " + msg);
	}

	public void execute(MutationTestSpec testSpec) {
		long start = System.currentTimeMillis();
		log("Creating mutants");
		Set<Mutant> mutantsToBeRun = getMutantGenerator().generateMutants(
				testSpec.getClassUnderTest(), testSpec.getOperators());

		log("Executing tests");
		Set<Mutant> survivors = getTestExecuter().executeTests(
				testSpec.getTestSet(), mutantsToBeRun);

		// generate report
		log("Generating report");
		getReportGenerator()
				.generateReport(testSpec, mutantsToBeRun, survivors);
		long elapsed = System.currentTimeMillis() - start;
		System.out.println("Result (class,time,mut,sur) "
				+ testSpec.getClassUnderTest() + "," + elapsed + ","
				+ mutantsToBeRun.size() + "," + survivors.size());
		log("Finished.");

	}

	public IMutantGenerator getMutantGenerator() {
		return mutantGenerator;
	}

	public void setMutantGenerator(IMutantGenerator mutantGenerator) {
		this.mutantGenerator = mutantGenerator;
	}

	public ITestExecuter getTestExecuter() {
		return testExecuter;
	}

	public void setTestExecuter(ITestExecuter testExecuter) {
		this.testExecuter = testExecuter;
	}

	public IReportGenerator getReportGenerator() {
		return reportGenerator;
	}

	public void setReportGenerator(IReportGenerator reportGenerator) {
		this.reportGenerator = reportGenerator;
	}
}
