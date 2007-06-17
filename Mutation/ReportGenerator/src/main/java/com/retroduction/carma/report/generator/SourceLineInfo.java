package com.retroduction.carma.report.generator;

import java.util.List;

import com.retroduction.carma.xmlreport.om.Mutant;

/**
 * represents information for a single source line
 * @author mike
 *
 */
public class SourceLineInfo {
	private long lineNo;
	private List<Mutant> mutantInfo;
	private String text;
	public long getLineNo() {
		return lineNo;
	}
	public List<Mutant> getMutantInfo() {
		return mutantInfo;
	}
	public String getText() {
		return text;
	}
	public SourceLineInfo(long lineNo, String text, List<Mutant> mutantInfo) {
		super();
		this.lineNo = lineNo;
		this.text = text;
		this.mutantInfo = mutantInfo;
	}
}
