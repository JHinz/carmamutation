/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.beans;

import java.util.List;

import com.retroduction.carma.xmlreport.om.Mutant;

/**
 * @author arau
 *
 */
public class SourceCodeBean {

	private List<SourceCodeEntryBean> codeEntries;

	private Mutant mutant;

	public SourceCodeBean(List<SourceCodeEntryBean> codeEntries, Mutant mutant) {
		super();
		this.codeEntries = codeEntries;
		this.mutant = mutant;
	}

	public List<SourceCodeEntryBean> getCodeEntries() {
		return codeEntries;
	}

	public Mutant getMutant() {
		return mutant;
	}

}
