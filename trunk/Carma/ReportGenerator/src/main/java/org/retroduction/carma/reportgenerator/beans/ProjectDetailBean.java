/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.retroduction.carma.report.om.SourceFile;

/**
 * @author arau
 * 
 */
public class ProjectDetailBean {

	private String projectName;

	private Timestamp creationDate;

	private String hostName;
	
	private TreeMap<String, SourceFile> sourceFiles = new TreeMap<String, SourceFile>();

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public List<SourceFile> getSourceFiles() {
		return new ArrayList<SourceFile>(this.sourceFiles.values());
	}

	public void setSourceFiles(List<SourceFile> sourceFiles) {

		this.sourceFiles = new TreeMap<String, SourceFile>();

		for (SourceFile file : sourceFiles) {
			this.sourceFiles.put(file.getPackageName() + "."
					+ file.getClassName(), file);
		}

	}

	public void addSourceFile(SourceFile file) {
		this.sourceFiles.put(file.getPackageName() + "." + file.getClassName(),
				file);
	}

	public SourceFile getSourceFile(String packageName, String className) {
		return this.sourceFiles.get(packageName + "." + className);
	}

}
