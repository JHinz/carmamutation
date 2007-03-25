package mut.driver;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import mut.IMutantGenerator;
import mut.IReportGenerator;
import mut.ITestExecuter;
import mut.Mutant;
import mut.MutationOperator;
import mut.MutationTestSpec;
import mut.log.ConsoleEventLogger;
import mut.log.Event;
import mut.log.IEventLogger;
import mut.util.StopWatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

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

	private IMutationTestsCreator testsCreator;

	private Set<MutationOperator> operators;

	private IEventLogger logger = new ConsoleEventLogger(
			MutationTestDriver.class);

	

	public void start() {

		StopWatch watch = new StopWatch();
		watch.start();
		List<MutationTestSpec> tests = getTestsCreator().createTests(
				getOperators());

		{
			Map<String, Object> params = new TreeMap<String, Object>();
			params.put("time", watch.stop());
			params.put("numTests", tests.size());
			getLogger().log(Event.TESTS_CREATED, params);
		}
		for (MutationTestSpec testSpec : tests) {
			watch.start();
			execute(testSpec);
			Map<String, Object> params = new TreeMap<String, Object>();
			params.put("time", watch.stop());
			params.put("classUnderTest", testSpec.getClassUnderTest());
			getLogger().log(Event.EXECUTION_FINISHED, params);
			
		}
	}

	public static void main(String[] args) throws MalformedURLException,
			FileNotFoundException {
		ApplicationContext factory = new FileSystemXmlApplicationContext("testconfig.xml");

		MutationTestDriver driver = (MutationTestDriver) factory
				.getBean("testDriver");
		driver.start();

	}



	private void log(String msg) {
		if (false) {
			return;
		}
		System.out.println(new Date() + " " + getClass().getSimpleName()
				+ " ## " + msg);
	}

	public void execute(MutationTestSpec testSpec) {
		Map<String, Object> statistics = new HashMap<String, Object>();
		StopWatch watch = new StopWatch();
		StopWatch totalWatch = new StopWatch();

		totalWatch.start();
		watch.start();
		log("Creating mutants");
		Set<Mutant> mutantsToBeRun = getMutantGenerator().generateMutants(
				testSpec.getClassUnderTest(), testSpec.getOperators());
		statistics.put("mutantCreationTime", watch.stop());
		
		log("Executing tests for: " +testSpec.getClassUnderTest());
	
		watch.start();
		Set<Mutant> survivors = getTestExecuter().executeTests(
				testSpec.getTestSet(), mutantsToBeRun);
		statistics.put("testExecution", watch.stop());

		// generate report
		log("Generating report");
		statistics.put("totalExecution", totalWatch.stop());

		getReportGenerator()
				.generateReport(testSpec, mutantsToBeRun, survivors, statistics);

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

	public IMutationTestsCreator getTestsCreator() {
		return testsCreator;
	}

	public void setTestsCreator(IMutationTestsCreator testsCreator) {
		this.testsCreator = testsCreator;
	}

	public Set<MutationOperator> getOperators() {
		return operators;
	}

	public void setOperators(Set<MutationOperator> operators) {
		this.operators = operators;
	}

	public IEventLogger getLogger() {
		return logger;
	}

	public void setLogger(IEventLogger logger) {
		this.logger = logger;
	}
}
