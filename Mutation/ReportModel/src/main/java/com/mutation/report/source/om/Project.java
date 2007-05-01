package com.mutation.report.source.om;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project {

	private Map<String, SourceFile> sourceFiles = new HashMap<String, SourceFile>();

	public List<SourceFile> getSourceFiles() {
		return new ArrayList<SourceFile>(sourceFiles.values());
	}

	public void setSourceFiles(List<SourceFile> sourceFiles) {

		this.sourceFiles = new HashMap<String, SourceFile>();

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
