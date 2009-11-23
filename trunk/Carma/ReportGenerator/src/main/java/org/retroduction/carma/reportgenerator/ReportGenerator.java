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

package org.retroduction.carma.reportgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.retroduction.carma.reportgenerator.beanbuilder.PackageListingBeanBuilder;
import org.retroduction.carma.reportgenerator.beanbuilder.ProjectSourceCodeFileListBeanBuilder;
import org.retroduction.carma.reportgenerator.beans.PackageDetailBean;
import org.retroduction.carma.reportgenerator.beans.ProjectSourceCodeFileListBean;
import org.retroduction.carma.reportgenerator.reporter.ClassListReporter;
import org.retroduction.carma.reportgenerator.reporter.ClassSnippetReporter;
import org.retroduction.carma.reportgenerator.reporter.PackageViewReporter;
import org.retroduction.carma.reportgenerator.reporter.ProjectViewReporter;
import org.retroduction.carma.reportgenerator.reporter.StyleSheetReporter;

import com.retroduction.carma.report.om.SourceFile;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * @author arau
 * 
 */
public class ReportGenerator {

	private Logger logger = Logger.getLogger(ReportGenerator.class.getName());

	public void perform(MutationRun report, File outputDirectory, List<File> sourceFolders) throws ReportingException,
			RendererException, IOException {

		FileUtils.forceMkdir(outputDirectory);
		FileUtils.cleanDirectory(outputDirectory);

		createStyleSheet(outputDirectory);

		createProjectReport(report, outputDirectory);

		createClassDetailsReport(report, outputDirectory, sourceFolders);

		createPackageReports(report, outputDirectory);

	}

	private void createStyleSheet(File outputDirectory) throws IOException {
		Writer outputWriter = new FileWriter(outputDirectory.getAbsolutePath() + "/" + "style.css");
		StyleSheetReporter reporter = new StyleSheetReporter();
		reporter.generateReport(outputWriter);
	}

	private void createPackageReports(MutationRun report, File outputDirectory) throws IOException {
		PackageListingBeanBuilder packageDetailBeanBuilder = new PackageListingBeanBuilder();
		List<PackageDetailBean> packageDetailBeans = packageDetailBeanBuilder.get(report);

		for (PackageDetailBean packageBean : packageDetailBeans) {
			ClassListReporter classListReporter = new ClassListReporter();
			FileWriter classListOutputWriter = new FileWriter(outputDirectory.getAbsolutePath() + "/"
					+ packageBean.getFqName() + ".html");
			classListReporter.generateReport(report, packageBean.getFqName(), classListOutputWriter);
		}
	}

	private void createProjectReport(MutationRun report, File outputDirectory) throws IOException {
		ProjectViewReporter reporter = new ProjectViewReporter();
		FileWriter outputWriter = new FileWriter(outputDirectory.getAbsolutePath() + "/" + "index.html");
		reporter.generateReport(report, outputWriter);
		outputWriter.close();
	}

	private void createClassDetailsReport(MutationRun report, File outputDirectory, List<File> sourceFolders)
			throws ReportingException {
		try {

			ProjectSourceCodeFileListBeanBuilder builder = new ProjectSourceCodeFileListBeanBuilder();
			ProjectSourceCodeFileListBean project = builder.getSourceCodeFileList(report, sourceFolders);

			if (!(outputDirectory.exists())) {
				outputDirectory.mkdirs();

			}

			ClassSnippetReporter classSnippetReporter = new ClassSnippetReporter();

			for (ClassUnderTest clazz : report.getClassUnderTest()) {
				SourceFile sourceFile = project.getSourceFile(clazz.getPackageName(), clazz.getClassName());

				if (sourceFile == null) {
					this.logger.warn("Missing source code file for: " + clazz.getPackageName() + "."
							+ clazz.getClassName());
					continue;
				}

				String reportFile = clazz.getPackageName() + "." + clazz.getClassName() + ".html";

				FileWriter writer = new FileWriter(outputDirectory.getAbsolutePath() + "/" + reportFile);

				classSnippetReporter
						.createReport(clazz, sourceFile, writer, ResourceBundle.getBundle("i18nTransition"));

			}

		} catch (Exception e) {
			throw new ReportingException("Report Generation Failed", e);
		}
	}

}
