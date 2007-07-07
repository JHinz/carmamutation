package com.retroduction.carma.report.generator.reportobjects;

/**
 * coverage information for a unit (package or class)
 * 
 * @author mike
 * 
 */
public class CoverageInfo implements Comparable<CoverageInfo> {
	/**
	 * fully qualified instance name
	 */
	private String fqName;
	
	private String shortName;

	private double coverage;

	private int numMutants;

	private int numSurvivors;
	
	private int numClasses;

	public int getNumClasses() {
		return numClasses;
	}

	public CoverageInfo(String fqName, String shortName, int numClasses, int numMutants, int numSurvivors) {
		super();
		this.fqName = fqName;
		this.shortName = shortName;
		this.numClasses = numClasses;
		if (numMutants == 0) {
			this.coverage = 1.0;
		} else {
			this.coverage = (double) (numMutants - numSurvivors) / (double) numMutants;
		}
		this.numMutants = numMutants;
		this.numSurvivors = numSurvivors;
	}

	public double getCoverage() {
		return coverage;
	}

	public String getFqName() {
		return fqName;
	}

	public int getNumMutants() {
		return numMutants;
	}

	public int getNumSurvivors() {
		return numSurvivors;
	}

	public int compareTo(CoverageInfo o) {
		return fqName.compareTo(o.fqName);
	}

	public String getShortName() {
		return shortName;
	}
}
