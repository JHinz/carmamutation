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
package org.retroduction.carma.reportgenerator.beanbuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.retroduction.carma.reportgenerator.beans.ProjectSourceCodeFileListBean;

import com.retroduction.carma.report.om.SourceFile;
import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * @author arau
 * 
 */
public class ProjectSourceCodeFileListBeanBuilder {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@SuppressWarnings("unchecked")
	public ProjectSourceCodeFileListBean getSourceCodeFileList(MutationRun report, List<File> sourceFolders) {

		ProjectSourceCodeFileListBean project = new ProjectSourceCodeFileListBean();

		for (File folderName : sourceFolders) {

			try {
				URL sourceFolderUrl = folderName.toURL();

				Iterator fileIterator = FileUtils.iterateFiles(folderName, new String[] { "java" }, true);

				while (fileIterator.hasNext()) {
					try {

						File sourceFileName = (File) fileIterator.next();

						URL sourceFileUrl = sourceFileName.toURL();

						SourceFile sourceFile = new SourceFile();

						sourceFile.setFileName(sourceFileName.getPath());
						sourceFile.setPackageName(this.extractPackageName(sourceFolderUrl, sourceFileUrl));
						sourceFile.setClassName(FilenameUtils.getBaseName(sourceFileUrl.toString()));

						sourceFile.setSourceText(FileUtils.readLines(new File(sourceFileUrl.getPath())));
						project.addSourceFile(sourceFile);
					} catch (IOException e) {
						logger.warn("Source folder " + folderName + " is invalid. Skipping ...");
						continue;
					}

				}
			} catch (IOException e) {
				logger.warn("Source folder " + folderName + " is invalid. Skipping ...");
				continue;
			}

		}

		return project;

	}

	private String extractPackageName(URL sourceFolderUrl, URL sourceFileUrl) {

		String file = FilenameUtils.getPathNoEndSeparator(sourceFileUrl.toString());
		String sourceFolder = FilenameUtils.getPathNoEndSeparator(sourceFolderUrl.toString());
		return file.substring(sourceFolder.length() + 1).replaceAll("/", ".");

	}

}
