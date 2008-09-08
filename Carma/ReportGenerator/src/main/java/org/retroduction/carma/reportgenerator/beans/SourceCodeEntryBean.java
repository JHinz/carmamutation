/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.beans;

/**
 * @author arau
 *
 */
public class SourceCodeEntryBean {

	private String code;
	private Integer lineNumber;

	public SourceCodeEntryBean(String code, Integer lineNumber) {
		super();
		this.code = code;
		this.lineNumber = lineNumber;
	}

	public String getCode() {
		return code;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

}
