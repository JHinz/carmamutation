package com.retroduction.carma.ant;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

import com.retroduction.carma.application.MavenTestExecuter;
import com.retroduction.carma.eventlisteners.SummaryCreatorEventListener.Summary;

public class AntCarmaTest extends Task {

	private Path dependencyClassPathUrls;

	private File classesDir;

	private File testClassesDir;

	private File reportFile = new File("report.xml");

	private File configFile = new File("carma.properties");

	public void setDependencyClassPathUrls(Path dependencyClassPathUrls) {
		this.dependencyClassPathUrls = dependencyClassPathUrls;
	}

	public void setClassesdir(File classesDir) {
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

	@Override
	public void execute() throws BuildException {

		super.execute();

		MavenTestExecuter mavenCarma = new MavenTestExecuter();

		mavenCarma.setClassesDir(classesDir);
		mavenCarma.setConfigFile(configFile);

		String[] paths = dependencyClassPathUrls.list();

		List<URL> urls = new ArrayList<URL>();
		for (String path : paths)
			try {
				urls.add(new URL("file:" + path));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}

		mavenCarma.setDependencyClassPathUrls(urls);
		mavenCarma.setReportFile(reportFile);
		mavenCarma.setTestClassesDir(testClassesDir);

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
		}
	}
}
