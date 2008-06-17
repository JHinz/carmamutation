/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.retroduction.carma.report.om.SourceFile;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.Mutant;

public class SourceInfoCreator {
	private SourceFile sourceFile;
	private Map<Long, List<Mutant>> lineToMutants = new HashMap<Long, List<Mutant>>();

	public SourceInfoCreator(ClassUnderTest classUnderTest, SourceFile sourceFile) {
		this.sourceFile = sourceFile;

		List<Mutant> mutants = classUnderTest.getMutant();
		for (Mutant m : mutants) {
			Long lineStart = m.getBaseSourceLineStart();
			Long lineEnd = m.getBaseSourceLineEnd();

			for (long line = lineStart; line <= lineEnd; line++) {

				if (!this.lineToMutants.containsKey(line)) {
					this.lineToMutants.put(line, new ArrayList<Mutant>(1));
				}
				this.lineToMutants.get(line).add(m);
			}
		}
	}

	public List<SourceLineInfo> createSourceInfo() {

		List<SourceLineInfo> list = new ArrayList<SourceLineInfo>();
		if (null == this.sourceFile.getSourceText()) {
			return list;
		}
		long lineNo = 1;
		for (String text : this.sourceFile.getSourceText()) {
			List<Mutant> mutants = this.lineToMutants.get(lineNo);

			SourceLineInfo info = new SourceLineInfo(lineNo, text.replaceAll("\\t", "        "), mutants);
			if (mutants != null)
				for (Mutant mutant : mutants) {
					if (mutant.getBaseSourceLineEnd() != 0
							&& mutant.getBaseSourceColumnEnd() >= text.length()
							&& mutant.getBaseSourceLineEnd() == lineNo) {
						System.err
								.println("Warning: End of statement after end of source line text: "
										+ lineNo + " " + text);
						System.err.println("ColumnStart: " + mutant.getBaseSourceColumnStart());
						System.err.println("ColumnEnd: " + mutant.getBaseSourceColumnEnd());
						System.err.println("Line Length: " + text.length());
					}
				}
			list.add(info);
			lineNo++;
		}
		return list;
	}
}
