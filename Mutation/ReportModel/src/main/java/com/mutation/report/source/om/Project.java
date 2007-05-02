package com.mutation.report.source.om;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Project {

	private TreeMap<String, SourceFile> sourceFiles = new TreeMap<String, SourceFile>();

	public List<SourceFile> getSourceFiles() {
		return new ArrayList<SourceFile>(sourceFiles.values());
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
