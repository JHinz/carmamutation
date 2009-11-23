/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */
package com.retroduction.carma.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.retroduction.carma.core.Core;
import com.retroduction.carma.core.ICoreConfigConsts;
import com.retroduction.carma.eventlisteners.CompositeEventListener;
import com.retroduction.carma.eventlisteners.SummaryCreatorEventListener;
import com.retroduction.carma.eventlisteners.SummaryCreatorEventListener.Summary;

public class CarmaTestExecuter {

	private static final String BEAN_MAVEN_PROJECT_CLASSES_DIR = "maven.project.classesDir";

	private static final String BEAN_MAVEN_PROJECT_TESTCLASSES_DIR = "maven.project.testclassesDir";

	private static final String BEAN_MAVEN_PROJECT_LIBRARIES = "maven.project.libraries";

	private List<URL> dependencyClassPathUrls;

	private File classesDir;

	private File testClassesDir;

	private File reportFile = new File("report.xml");

	private File configFile = new File("carma.properties");

	public Summary executeTests() {

		CarmaDriverSetup setup = new CarmaDriverSetup();
		Properties customProps = new Properties();
		try {
			customProps.load(new FileInputStream(this.configFile));

		} catch (IOException e) {
			throw new CarmaException("Failed to load configuration", e);
		}

		// merge multiple bean definition sources into application context
		GenericApplicationContext factory = new GenericApplicationContext();

		customProps.setProperty(ICoreConfigConsts.BEAN_REPORTFILE, this.reportFile.getPath());

		// configure maven specific bean references and add beans to parent
		// factory
		// the names in the parent factory should be unique and not overridden
		// by children, otherwise they would have no effect

		customProps.setProperty("project.classesdir.source", BEAN_MAVEN_PROJECT_CLASSES_DIR);
		factory.getBeanFactory().registerSingleton(BEAN_MAVEN_PROJECT_CLASSES_DIR, this.classesDir);

		customProps.setProperty("project.testclassesdir.source", BEAN_MAVEN_PROJECT_TESTCLASSES_DIR);
		factory.getBeanFactory().registerSingleton(BEAN_MAVEN_PROJECT_TESTCLASSES_DIR, this.testClassesDir);

		customProps.setProperty("project.libraries.source", BEAN_MAVEN_PROJECT_LIBRARIES);
		factory.getBeanFactory().registerSingleton(BEAN_MAVEN_PROJECT_LIBRARIES, this.dependencyClassPathUrls);

		setup.setParentContext(factory);
		setup.addCustomConfiguration(customProps);

		// add runtime parameters
		if (this.reportFile.getParentFile() != null) {
			this.reportFile.getParentFile().mkdirs();
		}

		factory.refresh();
		ApplicationContext ctx = setup.getApplicationContext();
		SummaryCreatorEventListener summaryCreator = new SummaryCreatorEventListener();
		((CompositeEventListener) ctx.getBean("eventListener")).getListeners().add(summaryCreator);

		Core driver = setup.getDriver();
		driver.execute();

		Summary sum = summaryCreator.createSummary();
		return sum;
	}

	public List<URL> getDependencyClassPathUrls() {
		return this.dependencyClassPathUrls;
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

	public void setReportFile(File reportFile) {
		this.reportFile = reportFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}

}
