package mut.driver;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import mut.EMutationType;
import mut.IMutantGenerator;
import mut.IReportGenerator;
import mut.ITestExecuter;
import mut.Mutant;
import mut.MutationTestSpec;
import mut.log.ConsoleEventLogger;
import mut.log.Event;
import mut.log.IEventLogger;
import mut.util.ProcessingInfoProvider;
import mut.util.StopWatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mutation.report.om.MutationRatio;
import com.mutation.report.om.MutationRun;
import com.mutation.report.om.MutationSet;
import com.mutation.report.om.ObjectFactory;
import com.mutation.report.om.ProcessingInfo;

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

	private Set<EMutationType> operators;

	private IEventLogger logger = new ConsoleEventLogger(MutationTestDriver.class);

	private MutationRun mutationRun;

	public void start() {

		mutationRun = new MutationRun();
		mutationRun.setMutationRatio(new MutationRatio());

		long start = System.currentTimeMillis();

		StopWatch watch = new StopWatch();
		watch.start();
		List<MutationTestSpec> tests = getTestsCreator().createTests(getOperators());

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

		long stop = System.currentTimeMillis();

		mutationRun.setProcessingInfo(new ProcessingInfoProvider().fillProcessInfo(start, stop));

		dumpWithJAXB(mutationRun);

	}

	private void dumpWithJAXB(MutationRun report) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("com.mutation.report.om");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			marshaller.marshal(report, new FileOutputStream("jaxbOutput2.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws MalformedURLException, FileNotFoundException {
		ApplicationContext factory = new FileSystemXmlApplicationContext("testconfig.xml");

		MutationTestDriver driver = (MutationTestDriver) factory.getBean("testDriver");
		driver.start();

	}

	private void log(String msg) {
		if (false) {
			return;
		}
		System.out.println(new Date() + " " + getClass().getSimpleName() + " ## " + msg);
	}

	public void execute(MutationTestSpec testSpec) {

		MutationSet mutationSet = new ObjectFactory().createMutationSet();
		mutationSet.setTargetClass(testSpec.getClassUnderTest());
		mutationRun.getMutationSet().add(mutationSet);

		StopWatch watch = new StopWatch();
		StopWatch totalWatch = new StopWatch();

		totalWatch.start();
		watch.start();

		log("Creating mutants");
		List<Mutant> mutantsToBeRun = getMutantGenerator().generateMutants(testSpec.getClassUnderTest(),
				testSpec.getOperators());

		log("Executing tests for: " + testSpec.getClassUnderTest());

		watch.start();

		List<Mutant> survivors = getTestExecuter().executeTests(mutationSet, testSpec.getTestSet(), mutantsToBeRun);

		log("Generating report");

		getReportGenerator().generateReport(testSpec, mutantsToBeRun, survivors);

		log("Finished.");

		mutationRun.setMutationRatio(new MutationRatio());

		mutationRun.getMutationRatio().setMutationCount(
				mutationRun.getMutationRatio().getMutationCount() + mutantsToBeRun.size());

		mutationRun.getMutationRatio().setSurvivorCount(
				mutationRun.getMutationRatio().getSurvivorCount() + survivors.size());

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

	public Set<EMutationType> getOperators() {
		return operators;
	}

	public void setOperators(Set<EMutationType> operators) {
		this.operators = operators;
	}

	public IEventLogger getLogger() {
		return logger;
	}

	public void setLogger(IEventLogger logger) {
		this.logger = logger;
	}
}
