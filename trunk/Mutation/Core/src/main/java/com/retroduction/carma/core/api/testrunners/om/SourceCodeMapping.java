package com.retroduction.carma.core.api.testrunners.om;

/**
 * Information about the mapping of a change to the original source code
 * 
 * @author mike
 * 
 */
public class SourceCodeMapping {
	
	@Override
	public String toString() {
		return className + " (" + sourceFile + ":" + lineNo + ")";
	}

	private int lineNo;

	private String sourceFile;

	private String className;

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
}
