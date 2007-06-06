package com.mutation.driver.maven;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.mutation.BasicDriver;
import com.mutation.runner.events.listener.SummaryCreatorEventListener;
import com.mutation.runner.events.listener.SummaryCreatorEventListener.Summary;
import com.mutation.testrunner.junit3.JUnitRunner;
import com.mutation.transform.TransitionGroupConfig;

public class MavenTestExecuter {
	private List<URL> dependencyClassPathUrls;
	private File classesDir;
	private File testClassesDir;
	private File logFile = new File("test.log");
	private File reportFile = new File("report.xml");
	private String testNamePattern = "Test";
	
	public Summary exeuteTests() throws MojoExecutionException {
		Properties properties = new Properties();
		properties.setProperty("classesDir", classesDir.getAbsolutePath());
		properties.setProperty("testClassesDir", testClassesDir.getAbsolutePath());
		properties.setProperty("log.filename", logFile.getAbsolutePath());
		reportFile.getParentFile().mkdirs();
		//properties.setProperty("report.filename", );

		// TODO maven config
		properties.setProperty("testCaseSuffix", testNamePattern);
		
		ClassPathResource springConfigResource = new ClassPathResource("mutationconfig.xml");
		XmlBeanFactory factory = new XmlBeanFactory(springConfigResource);
		
		factory.registerSingleton("classesDir", new File(classesDir.getAbsolutePath()));
		factory.registerSingleton("testClassesDir", new File(testClassesDir.getAbsolutePath()));
		factory.registerSingleton("libraries", getDependencyClassPathUrls());
		factory.registerSingleton("usedResolver", factory.getBean("classMatchResolver"));
		factory.registerSingleton("report.filename", reportFile.getAbsolutePath());
		
		PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
		propertyPlaceholderConfigurer.setProperties(properties);
		propertyPlaceholderConfigurer.postProcessBeanFactory(factory);

		JUnitRunner junitRunner = (JUnitRunner) factory.getBean("testRunner");
		// TODO use dependencies in mutation test executer
		// -> JUnitRunner setTestClassesLocAsFiles ... overwrite spring config?
		try {
			List<URL> junitRunnerClassPath = new ArrayList<URL>(); //getDependencyClassPathUrls();
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
		TransitionGroupConfig tgGroup = (TransitionGroupConfig) factory.getBean("operators");
		BasicDriver driver = (BasicDriver) factory.getBean("testDriver");
		driver.execute(tgGroup);

		SummaryCreatorEventListener summaryCreator = (SummaryCreatorEventListener) factory.getBean("summaryCreator");
		Summary sum = summaryCreator.createSummary();
		factory.destroySingletons();
		return sum;
	}

	public List<URL> getDependencyClassPathUrls() {
		return dependencyClassPathUrls;
	}

	public void setDependencyClassPathUrls(List<URL> dependencyClassPathUrls) {
		this.dependencyClassPathUrls = dependencyClassPathUrls;
	}

	public void setClassesDir(File classesDir) {
		this.classesDir = classesDir;
	}

	public void setTestClassesDir(File testClassesDir) {
		this.testClassesDir = testClassesDir;
	}

	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}

	public void setReportFile(File reportFile) {
		this.reportFile = reportFile;
	}

	public void setTestNamePattern(String testNamePattern) {
		this.testNamePattern = testNamePattern;
	}
}
