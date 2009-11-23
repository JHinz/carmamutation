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

package org.retroduction.carma.reportgenerator.beanbuilder;

import java.util.ArrayList;
import java.util.List;

import org.retroduction.carma.reportgenerator.beans.SourceCodeBean;
import org.retroduction.carma.reportgenerator.beans.SourceCodeEntryBean;

import com.retroduction.carma.report.om.SourceFile;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.Mutant;

/**
 * @author arau
 *
 */
public class SnippetBuilder {

	private final static int PRELINECOUNT = 2;
	private final static int POSTLINECOUNT = 2;

	private ClassUnderTest classReport = null;
	private SourceFile source = null;

	public SnippetBuilder(ClassUnderTest classReport, SourceFile source) {
		this.classReport = classReport;
		this.source = source;
	}

	public List<SourceCodeBean> getSnippets() {

		ArrayList<SourceCodeBean> result = new ArrayList<SourceCodeBean>();

		for (Mutant mutant : classReport.getMutant()) {

			SourceCodeBuilder builder = new SourceCodeBuilder(mutant);

			long lineCount = source.getSourceText().size();

			long lowerBound = mutant.getBaseSourceLineStart() - PRELINECOUNT;
			long upperBound = mutant.getBaseSourceLineEnd() + POSTLINECOUNT;

			if (lowerBound < 1) {
				lowerBound = 1;
			}

			if (upperBound > lineCount) {
				upperBound = lineCount;
			}

			List<String> sourceText = source.getSourceText();

			for (long lineNo = lowerBound; lineNo <= upperBound; lineNo++) {
				builder.setCodeLine(new SourceCodeEntryBean(sourceText.get((int) lineNo - 1), new Integer(
						(int) lineNo)));
			}

			result.add(builder.getSourceCode());
		}

		return result;

	}
}
