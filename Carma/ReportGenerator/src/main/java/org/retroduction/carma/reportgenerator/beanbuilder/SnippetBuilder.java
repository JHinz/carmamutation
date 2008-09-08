/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
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
