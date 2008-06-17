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
		return this.className + " (" + this.sourceFile + ":" + this.lineStart + ")";
	}

	private int lineStart;

	private int lineEnd;

	private int columnStart;

	private int columnEnd;

	private String sourceFile;

	private String className;

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

	public int getColumnStart() {
		return columnStart;
	}

	public void setColumnStart(int columnStart) {
		this.columnStart = columnStart;
	}

	public int getColumnEnd() {
		return columnEnd;
	}

	public void setColumnEnd(int columnEnd) {
		this.columnEnd = columnEnd;
	}

	public int getLineStart() {
		return lineStart;
	}

	public void setLineStart(int lineStart) {
		this.lineStart = lineStart;
	}

	public int getLineEnd() {
		return lineEnd;
	}

	public void setLineEnd(int lineEnd) {
		this.lineEnd = lineEnd;
	}
}
