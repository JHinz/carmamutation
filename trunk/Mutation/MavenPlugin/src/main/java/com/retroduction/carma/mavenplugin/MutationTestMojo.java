package com.retroduction.carma.mavenplugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import com.retroduction.carma.application.CarmaTestExecuter;
import com.retroduction.carma.eventlisteners.SummaryCreatorEventListener.Summary;
import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

/**
 * Goal which executes mutationtests.
 * 
 * @goal mtest
 * 
 * @phase test
 */

public class MutationTestMojo extends AbstractMojo {

	private Logger logger = LoggerFactory.getLogger(MutationTestMojo.class);
	
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
	 * The target location for the mutation report
	 * 
	 * @parameter expression="${carma.config}"
	 *            default-value="${basedir}/src/main/carma/carma.properties"
	 * @required
	 */
	private File carmaConfigFile;

	/**
	 * The set of dependencies required by the project
	 * 
	 * @parameter default-value="${project.artifacts}"
	 * @required
	 * @readonly
	 */
	private java.util.Set<Artifact> dependencies;

	public void execute() throws MojoExecutionException, MojoFailureException {
		Log log = this.getLog();

		log.info("Creating MutationTest report: " + this.reportFile);

		try {
			CarmaTestExecuter executer = new CarmaTestExecuter();
			executer.setClassesDir(this.classesDir);
			executer.setTestClassesDir(this.testClassesDir);
			executer.setDependencyClassPathUrls(this.getDependencyClassPathUrls());
			executer.setReportFile(this.reportFile);
			executer.setConfigFile(this.carmaConfigFile);

			Summary sum = executer.executeTests();
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(2);
			format.setMinimumFractionDigits(2);
			log.info("# --------------------------------------------------------------------------------");
			log.info("# CARMA TEST RESULTS SUMMARY ");
			log.info("#   Total time                : " + format.format(sum.timeSeconds) + " sec.");
			log.info("#   Classes/Tests             : " + sum.numClassesUnderTest + "/" + sum.numTests);
			log.info("#   Tests Per Class           : " + format.format(sum.testsPerClass));
			log.info("#   Mutants/Class             : " + format.format(sum.mutantsPerClass));
			log.info("#   Mutants/Survivors         : " + sum.numMutants + "/" + sum.numSurvivors);
			log.info("#   MutationCoverageRatio     : " + format.format(sum.coverageRatioPercentage) + " %");
			log.info("# --------------------------------------------------------------------------------");

		} catch (MalformedURLException e) {
			throw new MojoExecutionException("Could not handle dependency URLs", e);
		}

	}

	private List<URL> getDependencyClassPathUrls() throws MalformedURLException {

		List<URL> urls = new ArrayList<URL>();
		this.logger.debug("Setting dependencies from maven project dependencies");
		if (this.dependencies != null && !this.dependencies.isEmpty()) {
			for (Iterator<Artifact> it = this.dependencies.iterator(); it.hasNext();) {
				Artifact dep = (Artifact) it.next();
				this.logger.debug("Adding dependency: " + dep.getFile().toURL());
				urls.add(dep.getFile().toURL());
			}
		}
		return urls;

	}

	public void setCarmaConfigFile(File carmaConfigFile) {
		this.carmaConfigFile = carmaConfigFile;
	}
}
