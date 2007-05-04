package com.mutation.driver.maven;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.mutation.BasicDriver;
import com.mutation.runner.EMutationOperator;
import com.mutation.runner.events.listener.SummaryCreatorEventListener;
import com.mutation.runner.events.listener.SummaryCreatorEventListener.Summary;
import com.mutation.testrunner.JUnitRunner;

/**
 * Goal which executes mutationtests.
 * 
 * @goal mtest
 * 
 * @phase test
 */

public class MutationTestMojo extends AbstractMojo {

	/**
	 * The location of the generated class files
	 * 
	 * @parameter expression="${mutation.sourceDir}"
	 *            default-value="${project.build.sourceDirectory}"
	 * @required
	 */
	private File sourceDir;

	/**
	 * The location of the generated class files
	 * 
	 * @parameter expression="${mutation.classesDir}"
	 *            default-value="${project.build.outputDirectory}"
	 * @required
	 */
	private File classesDir;

	/**
	 * The location of the generated unit test class files
	 * 
	 * @parameter expression="${mutation.testClassesDir}"
	 *            default-value="${project.build.testOutputDirectory}"
	 * @required
	 */
	private File testClassesDir;

	/**
	 * The target location for the mutation report
	 * 
	 * @parameter expression="${mutation.reportFile}"
	 *            default-value="${project.reporting.outputDirectory}/mutationtest/mutationTestReport.xml"
	 * @required
	 */
	private File reportFile;

	/**
	 * The name of the log file for mutation test driver
	 * 
	 * @parameter expression="${mutation.reportFile}"
	 *            default-value="${project.reporting.outputDirectory}/mutationtest/mutationTests.log"
	 * @required
	 */
	private File logFile;

	/**
	 * The set of dependencies required by the project
	 * 
	 * @parameter default-value="${project.artifacts}"
	 * @required
	 * @readonly
	 */
	private java.util.Set dependencies;

	/**
	 * local maven repository
	 * 
	 * @parameter default-value="${localRepository}"
	 * @required
	 * @readonly
	 */
	private org.apache.maven.artifact.repository.ArtifactRepository repository;

	public static void main(String[] args) throws MojoExecutionException, MojoFailureException {
		// just for easy debugging
		MutationTestMojo mojo = new MutationTestMojo();
		mojo.classesDir = new File("../SampleProjectUnderTest/target/classes");
		mojo.logFile = new File("target/mutation.log");
		mojo.reportFile = new File("target/report.xml");
		mojo.sourceDir = new File("src/main/java");
		mojo.testClassesDir = new File("../SampleProjectUnderTest/target/test-classes");
		mojo.execute();
	}

	public void execute() throws MojoExecutionException, MojoFailureException {
		System.out.println("++++ exeucting mutation tests +++++");

		System.out.println("source dir         : " + sourceDir);
		System.out.println("classes dir        : " + classesDir);
		System.out.println("test classes dir   : " + testClassesDir);
		System.out.println("MutationTest report: " + reportFile);
		super.getLog().info("§");
		Properties properties = new Properties();
		properties.setProperty("sourceDir", sourceDir.getAbsolutePath());
		properties.setProperty("classesDir", classesDir.getAbsolutePath());
		properties.setProperty("testClassesDir", testClassesDir.getAbsolutePath());
		properties.setProperty("log.filename", logFile.getAbsolutePath());
		reportFile.getParentFile().mkdirs();
		properties.setProperty("report.filename", reportFile.getAbsolutePath());

		// TODO maven config
		properties.setProperty("testCaseSuffix", "Test");

		ClassPathResource springConfigResource = new ClassPathResource("mutationconfig-maven.xml");
		XmlBeanFactory factory = new XmlBeanFactory(springConfigResource);
		PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
		System.out.println("classesDir:" + properties.getProperty("classesDir"));
		propertyPlaceholderConfigurer.setProperties(properties);
		propertyPlaceholderConfigurer.postProcessBeanFactory(factory);

		JUnitRunner junitRunner = (JUnitRunner) factory.getBean("testRunner");
		// TODO use dependencies in mutation test executer
		// -> JUnitRunner setTestClassesLocAsFiles ... overwrite spring config?
		try {
			List<URL> junitRunnerClassPath = getDependencyClassPathUrls();
			// add testClassesDir junitRunnerClassPath
			// add classesDir

			junitRunnerClassPath.add(classesDir.toURL());
			junitRunnerClassPath.add(testClassesDir.toURL());

			junitRunner.setTestClassesLocations(junitRunnerClassPath.toArray(new URL[junitRunnerClassPath.size()]));

		} catch (MalformedURLException e) {
			throw new MojoExecutionException("Could not handle dependency URLs", e);
		}

		// factory.registerShutdownHook();

		// TODO read from config
		List<EMutationOperator> operators = (List<EMutationOperator>) factory.getBean("operators");
		BasicDriver driver = (BasicDriver) factory.getBean("testDriver");
		driver.execute(operators);

		SummaryCreatorEventListener summaryCreator = (SummaryCreatorEventListener) factory.getBean("summaryCreator");
		Summary sum = summaryCreator.createSummary();
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);
		Log log = getLog();
		log.info("# --------------------------------------------------------------------------------");
		log.info("# TEST RESULTS SUMMARY ");
		log.info("#   Total time                : " + format.format( sum.timeSeconds ) + " sec.");
		log.info("#   Classes/Tests             : " + sum.numClassesUnderTest + "/" + sum.numTests);
		log.info("#   Tests Per Class           : " + format.format( sum.testsPerClass ));
		log.info("#   Mutants/Class             : " + format.format( sum.mutantsPerClass ));
		log.info("#   Mutants/Survivors         : " + sum.numMutants + "/" + sum.numSurvivors);
		log.info("#   SurvivorRatio             : " + format.format( sum.survivorPercentage ) + " %");
		log.info("# --------------------------------------------------------------------------------");
		factory.destroySingletons();

	}

	private List<URL> getDependencyClassPathUrls() throws MalformedURLException {

		List<URL> urls = new ArrayList<URL>();
		// that line causes the classcastexception
		// urls.add(new URL("file:/C:/Dokumente und
		// Einstellungen/mike/.m2/repository/junit/junit/3.8.1/junit-3.8.1.jar"));
		// return urls;
		System.out.println("!!!!!! dependencies: " + dependencies);
		if (dependencies != null && !dependencies.isEmpty()) {
			for (Iterator it = dependencies.iterator(); it.hasNext();) {
				Artifact dep = (Artifact) it.next();
				System.out.println("### Dependency: " + dep.getFile().toURL());
				urls.add(dep.getFile().toURL());
			}
		}
		return urls;

	}
}
