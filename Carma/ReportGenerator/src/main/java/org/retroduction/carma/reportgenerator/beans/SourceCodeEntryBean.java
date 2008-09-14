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

	private final static int TAB_WIDTH = 8;
	private final static String TAB_REPLACEMENT = "        ";

	private String code;
	private Integer lineNumber;
	private int indent;

	public SourceCodeEntryBean(String code, Integer lineNumber) {
		super();

		code = code.replaceAll("\t", TAB_REPLACEMENT);

		int cutoffIndex = 0;
		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i) == ' ') {
				indent++;
				cutoffIndex++;
				continue;
			}

			break;

		}
		this.code = code; // .substring(cutoffIndex);
		this.lineNumber = lineNumber;
	}

	public String getCode() {
		return code;
	}

	public int getIndent() {
		return indent;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

}
