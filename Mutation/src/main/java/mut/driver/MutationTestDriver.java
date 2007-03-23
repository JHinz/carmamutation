package mut.driver;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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
import mut.annotations.MutationPair;
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

	private static IMutantGenerator mutantGenerator;

	private static ITestExecuter testExecuter;

	private static IReportGenerator reportGenerator;

	private void setUpDriver(String originalClassesLoc, String testClassesLoc)
			throws MalformedURLException {

		{
			// - Initialize mutant generator
			// Location of the original classes under test
			File originalClassPath = new File(originalClassesLoc);
			ASMMutantCreator mutantGenerator = new ASMMutantCreator(
					originalClassPath);

			MutationTestDriver.setMutantGenerator(mutantGenerator);
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
			setTestExecuter(testExecuter);
		}
		{
			// - Initialize report generator
			IReportGenerator reportGenerator = new ConsoleReportGenerator();
			setReportGenerator(reportGenerator);
		}

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
			System.out.println("Usage: java "
					+ MutationTestDriver.class.getName()
					+ " unitTestClassName1 unitTestClassName2 ...");
			return;
		}

		String originalClassesLoc = System
				.getProperty("classes",
						"F:/workspaces/mutationTesting/commons-codec-1.3/target/classes");
		String testClassesLoc = System
				.getProperty("testclasses",
						"F:/workspaces/mutationTesting/commons-codec-1.3/target/testclasses");

		new MutationTestDriver(args, originalClassesLoc, testClassesLoc);

	}

	private MutationTestDriver(String[] args, String originalClassesLoc,
			String testClassesLoc) throws MalformedURLException {

		setUpDriver(originalClassesLoc, testClassesLoc);

		for (int i = 0; i < args.length; i++) {

			String unitTestName = args[i]; // "org.apache.commons.codec.net.QCodecTest";

			String classUnderTest = null;

			try {
				String urlString = "file:" + testClassesLoc;

				URLClassLoader loader = new URLClassLoader(new URL[] { new URL(
						urlString) });

				Class unitTestClass = Class.forName(unitTestName, true, loader);

				boolean classHasAnnotation = unitTestClass
						.isAnnotationPresent(MutationPair.class);

				if (classHasAnnotation) {

					MutationPair pair = Class.forName(unitTestName, false,
							loader).getAnnotation(MutationPair.class);

					classUnderTest = pair.testCaseClassName();
					System.out.println("Annotation found for class "
							+ unitTestName);
				} else {
					classUnderTest = resolveTestNameForUnit(unitTestName);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<String> unitTestClasses = new ArrayList<String>();
			unitTestClasses.add(unitTestName);

			{
				// Define test spec

				// Mutation operators to be applied
				Set<MutationOperator> operators = new HashSet<MutationOperator>();
				operators.add(MutationOperator.ROR);

				MutationTestSpec testSpec = new MutationTestSpec();
				testSpec.setClassUnderTest(classUnderTest);
				testSpec.setTestSet(unitTestClasses);
				testSpec.setOperators(operators);

				execute(testSpec);
			}
		}
	}

	private String resolveTestNameForUnit(String unitTestName) {
		if (unitTestName.toLowerCase().endsWith("testcase"))
			return unitTestName.substring(0, unitTestName.length()
					- "testcase".length());

		if (unitTestName.toLowerCase().endsWith("test"))
			return unitTestName.substring(0, unitTestName.length()
					- "test".length());

		return "";

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

	public static IMutantGenerator getMutantGenerator() {
		return mutantGenerator;
	}

	public static void setMutantGenerator(IMutantGenerator mutantGenerator) {
		MutationTestDriver.mutantGenerator = mutantGenerator;
	}

	public static ITestExecuter getTestExecuter() {
		return testExecuter;
	}

	public static void setTestExecuter(ITestExecuter testExecuter) {
		MutationTestDriver.testExecuter = testExecuter;
	}

	public static IReportGenerator getReportGenerator() {
		return reportGenerator;
	}

	public static void setReportGenerator(IReportGenerator reportGenerator) {
		MutationTestDriver.reportGenerator = reportGenerator;
	}
}
