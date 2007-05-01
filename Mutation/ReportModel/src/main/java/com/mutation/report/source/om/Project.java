package com.mutation.report.source.om;

import java.util.ArrayList;
import java.util.List;

public class Project {
	
	private List<SourceFile> sourceFiles = new ArrayList<SourceFile>();

	public List<SourceFile> getSourceFiles() {
		return sourceFiles;
	}

	public void setSourceFiles(List<SourceFile> sourceFiles) {
		this.sourceFiles = sourceFiles;
	} 
	
	public void addSourceFile(SourceFile file) {
		
		if ( this.sourceFiles == null) {
			this.sourceFiles = new ArrayList<SourceFile>();
		}
		this.sourceFiles.add(file);
	}

}
