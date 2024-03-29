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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.retroduction.carma.reportgenerator.ReportGenerator;

import com.retroduction.carma.xmlreport.om.MutationRun;
import com.retroduction.carma.xmlreport.utilities.ReportModelLoader;

/**
 * @author arau
 * 
 */
public class CarmaReport extends org.apache.tools.ant.Task {

	/**
	 * The location of the generated class files
	 */
	private String sourceDir;

	/**
	 * The location for the mutation report
	 */
	private String reportFile;

	/**
	 * The target location for the mutation site report
	 */
	private String outputDirectory;

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	@Override
	public void execute() {
		ClassLoader threadClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader thisClassLoader = this.getClass().getClassLoader();

		Thread.currentThread().setContextClassLoader(thisClassLoader);
		try {
			ReportModelLoader loader = new ReportModelLoader();

			MutationRun mutationRun;
			try {
				mutationRun = loader.loadReportModel(new File(this.reportFile));
			} catch (FileNotFoundException e) {
				this.log(e, 2);
				throw new BuildException("Report File not found", e);
			} catch (JAXBException e) {
				this.log(e, 2);
				throw new BuildException("Invalid Report File", e);
			}
			ReportGenerator reportGenerator = new ReportGenerator();

			List<File> sourceDirectories = new ArrayList<File>();
			sourceDirectories.add(new File(this.sourceDir));
			reportGenerator.perform(mutationRun, new File(this.outputDirectory), sourceDirectories);

			this.log("# --------------------------------------------------------------------------------");
			this.log("# Mutation Site report generated. Output directory: " + this.outputDirectory);
			this.log("# --------------------------------------------------------------------------------");
		} catch (Exception e) {
			this.log(e, Project.MSG_ERR);
			e.printStackTrace();
			throw new BuildException("Error during writing report.", e);
		} finally {
			Thread.currentThread().setContextClassLoader(threadClassLoader);
		}
	}
}
