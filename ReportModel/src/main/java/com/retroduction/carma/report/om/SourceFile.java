package com.retroduction.carma.report.om;

import java.util.List;

public class SourceFile {

	private List<String> sourceText;

	private String packageName;

	private String className;

	private String fileName;

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<String> getSourceText() {
		return this.sourceText;
	}

	public void setSourceText(List<String> sourceText) {
		this.sourceText = sourceText;
	}

}
