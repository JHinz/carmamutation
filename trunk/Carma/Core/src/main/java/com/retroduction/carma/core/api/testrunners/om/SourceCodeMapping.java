/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
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
		return this.className + " (" + this.sourceFile + ":" + this.lineNo + ")";
	}

	private int lineNo;

	private String sourceFile;

	private String className;

	public int getLineNo() {
		return this.lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSourceFile() {
		return this.sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
}
