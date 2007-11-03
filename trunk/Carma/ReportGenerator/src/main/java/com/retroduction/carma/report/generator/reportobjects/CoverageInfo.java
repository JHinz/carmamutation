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
	
	private int numTests;

	public int getNumClasses() {
		return this.numClasses;
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
		return this.coverage;
	}

	public String getFqName() {
		return this.fqName;
	}

	public int getNumMutants() {
		return this.numMutants;
	}

	public int getNumSurvivors() {
		return this.numSurvivors;
	}

	public int compareTo(CoverageInfo o) {
		return this.fqName.compareTo(o.fqName);
	}

	public String getShortName() {
		return this.shortName;
	}

	public int getNumTests() {
		return numTests;
	}

	public void setNumTests(int numTests) {
		this.numTests = numTests;
	}
	
	public String getLevel(){
		if(0 == getNumMutants()){
			return "nomutations";
		}
		if( 0 == getNumTests() ){
			return "untested";
		}
		
		if( 0.8 < getCoverage() ){
			return "good";
		}
		
		if( 0.4 < getCoverage() ){
			return "medium";
		}
		
		return "bad";
	}
}
