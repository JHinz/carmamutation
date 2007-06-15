package com.mutation.driver.maven;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.retroduction.carma.application.Carma;
import com.retroduction.carma.application.ICarmaConfigConsts;
import com.retroduction.carma.core.ICoreConfigConsts;
import com.retroduction.carma.core.api.TransitionGroupConfig;
import com.retroduction.carma.core.runner.events.listener.CompositeEventListener;
import com.retroduction.carma.core.runner.events.listener.SummaryCreatorEventListener;
import com.retroduction.carma.core.runner.events.listener.SummaryCreatorEventListener.Summary;

public class MavenTestExecuter {
	private List<URL> dependencyClassPathUrls;

	private File classesDir;

	private File testClassesDir;

	private File logFile = new File("test.log");

	private File reportFile = new File("report.xml");

	private String testNamePattern = "Test";

	public Summary exeuteTests() throws MojoExecutionException {
		// TODO how to merge configurations? take base config, add maven
		// specific attributes a) runtime attibutes, b) external file - refresh
		// config

		// merge multiple bean definition sources into application context
		GenericApplicationContext factory = new GenericApplicationContext();
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(factory);
		// add base configuration from classpath
		xmlReader.loadBeanDefinitions(new ClassPathResource(ICoreConfigConsts.EVENTLISTENER_CONFIG_FILE));
		xmlReader.loadBeanDefinitions(new ClassPathResource(ICoreConfigConsts.RUNNER_CONFIG_FILE));
		xmlReader.loadBeanDefinitions(new ClassPathResource(ICoreConfigConsts.TRANSITIONS_CONFIG_FILE));

		xmlReader.loadBeanDefinitions(new ClassPathResource(ICarmaConfigConsts.CARMA_APPLICATION_CONFIG_FILE));

		// add custom configuration from file
		xmlReader.loadBeanDefinitions(new FileSystemResource("carma.xml"));

		// TODO reportfile bean should use File instead of String
		factory.getBeanFactory().registerSingleton(ICoreConfigConsts.BEAN_REPORTFILE, reportFile.getPath());
		factory.getBeanFactory().registerSingleton(ICoreConfigConsts.BEAN_CLASSESLOCATIONS, classesDir);
		factory.getBeanFactory().registerSingleton(ICoreConfigConsts.BEAN_TESTCLASSESLOCATIONS, testClassesDir);
		factory.getBeanFactory().registerSingleton(ICoreConfigConsts.BEAN_LIBRARIES, dependencyClassPathUrls);

		// initialize factory
		factory.refresh();

		// add runtime parameters
		reportFile.getParentFile().mkdirs();

		SummaryCreatorEventListener summaryCreator = new SummaryCreatorEventListener();
		((CompositeEventListener) factory.getBean("eventListener")).getListeners().add(summaryCreator);

		Carma driver = (Carma) factory.getBean(ICarmaConfigConsts.BEAN_CARMA);
		driver.execute();

		Summary sum = summaryCreator.createSummary();
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
