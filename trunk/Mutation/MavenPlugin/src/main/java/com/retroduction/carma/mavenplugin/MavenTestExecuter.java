package com.retroduction.carma.mavenplugin;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.retroduction.carma.core.Core;
import com.retroduction.carma.core.ICarmaConfigConsts;
import com.retroduction.carma.core.ICoreConfigConsts;
import com.retroduction.carma.eventlisteners.CompositeEventListener;
import com.retroduction.carma.eventlisteners.SummaryCreatorEventListener;
import com.retroduction.carma.eventlisteners.SummaryCreatorEventListener.Summary;

public class MavenTestExecuter {
	private List<URL> dependencyClassPathUrls;

	private File classesDir;

	private File testClassesDir;

	private File logFile = new File("test.log");

	private File reportFile = new File("report.xml");

	private String testNamePattern = "Test";

	public Summary executeTests(URL configuration) throws MojoExecutionException {
		// TODO how to merge configurations? take base config, add maven
		// specific attributes a) runtime attibutes, b) external file - refresh
		// config

		// merge multiple bean definition sources into application context
		GenericApplicationContext factory = new GenericApplicationContext();
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(factory);
		// add base configuration from classpath
		xmlReader.loadBeanDefinitions(new FileSystemResource(configuration.getFile()));

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

		Core driver = (Core) factory.getBean(ICarmaConfigConsts.CORE_BEAN);
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
