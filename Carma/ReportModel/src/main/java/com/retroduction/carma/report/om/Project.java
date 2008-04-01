/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.om;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Project {

	private TreeMap<String, SourceFile> sourceFiles = new TreeMap<String, SourceFile>();

	public List<SourceFile> getSourceFiles() {
		return new ArrayList<SourceFile>(this.sourceFiles.values());
	}

	public void setSourceFiles(List<SourceFile> sourceFiles) {

		this.sourceFiles = new TreeMap<String, SourceFile>();

		for (SourceFile file : sourceFiles) {
			this.sourceFiles.put(file.getPackageName() + "." + file.getClassName(), file);
		}

	}

	public void addSourceFile(SourceFile file) {
		this.sourceFiles.put(file.getPackageName() + "." + file.getClassName(), file);
	}

	public SourceFile getSourceFile(String packageName, String className) {
		return this.sourceFiles.get(packageName + "." + className);
	}

}
