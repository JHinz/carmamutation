package com.retroduction.carma.ant;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Commandline;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Environment;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.PropertySet;
import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import com.retroduction.carma.application.MavenTestExecuter;
import com.retroduction.carma.eventlisteners.SummaryCreatorEventListener.Summary;

public class AntCarmaTest extends Task {

	private SysProperties sysProperties = new SysProperties();

	private Path dependencyClassPathUrls;

	private String classesDir;

	private String testClassesDir;

	private String reportFile = "report.xml";

	private String configFile = "carma.properties";

	public void setDependencyClassPathUrls(Path dependencyClassPathUrls) {
		this.dependencyClassPathUrls = dependencyClassPathUrls;
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
		line.addSysproperties(sysProperties);
		line.getSystemProperties().setSystem();
		
		ClassLoader threadClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader thisClassLoader = this.getClass().getClassLoader();

		Thread.currentThread().setContextClassLoader(thisClassLoader);

		MavenTestExecuter mavenCarma = new MavenTestExecuter();

		mavenCarma.setClassesDir(new File(classesDir));
		mavenCarma.setConfigFile(new File(configFile));

		String[] paths = dependencyClassPathUrls.list();

		List<URL> urls = new ArrayList<URL>();
		for (String path : paths)
			try {
				urls.add(new URL("file:" + path));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}

		mavenCarma.setDependencyClassPathUrls(urls);
		mavenCarma.setReportFile(new File(reportFile));
		mavenCarma.setTestClassesDir(new File(testClassesDir));

		try {
			mavenCarma.executeTests();

			Summary sum = mavenCarma.executeTests();
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(2);
			format.setMinimumFractionDigits(2);
			log("# --------------------------------------------------------------------------------");
			log("# TEST RESULTS SUMMARY ");
			log("#   Total time                : " + format.format(sum.timeSeconds) + " sec.");
			log("#   Classes/Tests             : " + sum.numClassesUnderTest + "/" + sum.numTests);
			log("#   Tests Per Class           : " + format.format(sum.testsPerClass));
			log("#   Mutants/Class             : " + format.format(sum.mutantsPerClass));
			log("#   Mutants/Survivors         : " + sum.numMutants + "/" + sum.numSurvivors);
			log("#   MutationCoverageRatio     : " + format.format(sum.coverageRatioPercentage) + " %");
			log("# --------------------------------------------------------------------------------");
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
		sysProperties.addVariable(sysp);
	}

	/**
	 * Add a set of system properties.
	 * 
	 * @param sysp
	 *            a set of properties.
	 */
	public void addSyspropertyset(PropertySet sysp) {
		sysProperties.addSyspropertyset(sysp);
	}

	/**
	 * Add a set of system properties.
	 * 
	 * @param sysp
	 *            a set of properties.
	 * @since Ant 1.6.3
	 */
	public void addSysproperties(SysProperties sysp) {
		sysProperties.addSysproperties(sysp);
	}

}
