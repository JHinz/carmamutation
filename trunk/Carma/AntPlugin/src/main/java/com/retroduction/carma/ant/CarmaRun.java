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
package com.retroduction.carma.ant;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Environment;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.PropertySet;
import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import com.retroduction.carma.application.CarmaTestExecuter;
import com.retroduction.carma.eventlisteners.SummaryCreatorEventListener.Summary;

public class CarmaRun extends Task {

	private SysProperties sysProperties = new SysProperties();

	private Path dependencypath;

	private String classesDir;

	private String testClassesDir;

	private String reportFile = "report.xml";

	private String configFile = "carma.properties";

	/**
	 * Set the dependencypath to be used for this compilation.
	 * 
	 * @param dependencypath
	 *            an Ant Path object containing the compilation classpath.
	 */
	public void setDependencypath(Path classpath) {
		if (dependencypath == null) {
			dependencypath = classpath;
		} else {
			dependencypath.append(classpath);
		}
	}

	/**
	 * Gets the dependencypath to be used for this compilation.
	 * 
	 * @return the class path
	 */
	public Path getDependencypath() {
		return dependencypath;
	}

	/**
	 * Adds a path to the dependencypath.
	 * 
	 * @return a class path to be configured
	 */
	public Path createDependencypath() {
		if (dependencypath == null) {
			dependencypath = new Path(getProject());
		}
		return dependencypath.createPath();
	}

	/**
	 * Adds a reference to a classpath defined elsewhere.
	 * 
	 * @param r
	 *            a reference to a classpath
	 */
	public void setDependencypathRef(org.apache.tools.ant.types.Reference r) {
		createDependencypath().setRefid(r);
	}

	public void setClassesdir(String classesDir) {
		this.classesDir = classesDir;
	}

	public void setTestClassesDir(String testClassesDir) {
		this.testClassesDir = testClassesDir;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	@Override
	public void execute() throws BuildException {

		super.execute();

		CommandlineJava line = new CommandlineJava();
		line.addSysproperties(this.sysProperties);
		line.getSystemProperties().setSystem();

		ClassLoader threadClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader thisClassLoader = this.getClass().getClassLoader();

		Thread.currentThread().setContextClassLoader(thisClassLoader);

		CarmaTestExecuter mavenCarma = new CarmaTestExecuter();

		mavenCarma.setClassesDir(new File(this.classesDir));
		mavenCarma.setConfigFile(new File(this.configFile));

		List<URL> urls = new ArrayList<URL>();
		if (null != getDependencypath()) {
			String[] paths = getDependencypath().list();

			for (String path : paths)
				try {
					urls.add(new URL("file:" + path));
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
		}
		mavenCarma.setDependencyClassPathUrls(urls);

		mavenCarma.setReportFile(new File(this.reportFile));
		mavenCarma.setTestClassesDir(new File(this.testClassesDir));

		try {

			Summary sum = mavenCarma.executeTests();
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(2);
			format.setMinimumFractionDigits(2);
			this.log("# --------------------------------------------------------------------------------");
			this.log("# TEST RESULTS SUMMARY ");
			this.log("#   Total time                : " + format.format(sum.timeSeconds) + " sec.");
			this.log("#   Classes/Tests             : " + sum.numClassesUnderTest + "/" + sum.numTests);
			this.log("#   Tests Per Class           : " + format.format(sum.testsPerClass));
			this.log("#   Mutants/Class             : " + format.format(sum.mutantsPerClass));
			this.log("#   Mutants/Survivors         : " + sum.numMutants + "/" + sum.numSurvivors);
			this.log("#   MutationCoverageRatio     : " + format.format(sum.coverageRatioPercentage) + " %");
			this.log("# --------------------------------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException("Failure running Carma. ", e);
		} finally {
			Thread.currentThread().setContextClassLoader(threadClassLoader);
		}
	}

	/**
	 * Add a system property.
	 * 
	 * @param sysp
	 *            a property to be set in the JVM.
	 */
	public void addSysproperty(Environment.Variable sysp) {
		this.sysProperties.addVariable(sysp);
	}

	/**
	 * Add a set of system properties.
	 * 
	 * @param sysp
	 *            a set of properties.
	 */
	public void addSyspropertyset(PropertySet sysp) {
		this.sysProperties.addSyspropertyset(sysp);
	}

	/**
	 * Add a set of system properties.
	 * 
	 * @param sysp
	 *            a set of properties.
	 * @since Ant 1.6.3
	 */
	public void addSysproperties(SysProperties sysp) {
		this.sysProperties.addSysproperties(sysp);
	}

	

}
