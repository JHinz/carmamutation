/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
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
		this.code = code;
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
