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

import com.retroduction.carma.xmlreport.om.Mutant;

/**
 * @author arau
 *
 */
public class SourceCodeBuilder {

	private List<SourceCodeEntryBean> codeEntries;

	private Integer lowestLineNumber;
	private Integer highestLineNumber;

	private Mutant mutant;

	public SourceCodeBuilder(Mutant mutant) {
		super();
		this.codeEntries = new ArrayList<SourceCodeEntryBean>();
		this.mutant = mutant;
	}

	public void setCodeLine(SourceCodeEntryBean entry) {

		if (codeEntries.size() == 0) {
			this.codeEntries.add(entry);
			lowestLineNumber = entry.getLineNumber();
			highestLineNumber = entry.getLineNumber();
		} else {
			insertEntry(entry);
		}

		lowestLineNumber = (lowestLineNumber >= entry.getLineNumber()) ? lowestLineNumber = entry
				.getLineNumber() : lowestLineNumber;

		highestLineNumber = (highestLineNumber <= entry.getLineNumber()) ? highestLineNumber = entry
				.getLineNumber() : highestLineNumber;

	}

	private void insertEntry(SourceCodeEntryBean entry) {

		if (entry.getLineNumber() > highestLineNumber) {
			for (int i = highestLineNumber + 1; i <= entry.getLineNumber(); i++) {
				this.codeEntries.add(new SourceCodeEntryBean("", i));
			}
			this.codeEntries.set(entry.getLineNumber() - lowestLineNumber, entry);
			return;
		}

		if (entry.getLineNumber() < lowestLineNumber) {
			for (int i = lowestLineNumber - 1; i > entry.getLineNumber(); i--) {
				this.codeEntries.add(0, (new SourceCodeEntryBean("", i)));
			}
			this.codeEntries.add(0, entry);
			return;
		}

		this.codeEntries.set(entry.getLineNumber() - lowestLineNumber, entry);

	}

	public SourceCodeBean getSourceCode() {
		return new SourceCodeBean(this.codeEntries, mutant);
	}

}
