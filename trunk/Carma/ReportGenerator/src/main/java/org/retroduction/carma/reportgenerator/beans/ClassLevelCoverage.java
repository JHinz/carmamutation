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
public class ClassLevelCoverage implements Comparable<ClassLevelCoverage> {

	private String fqName;

	private String shortName;

	private Double coverage;

	private long numberOfMutants;

	private long numberOfSurvivors;

	private int numTests;

	public ClassLevelCoverage(String fqName, String shortName, long numMutants, long numSurvivors) {
		super();
		this.fqName = fqName;
		this.shortName = shortName;
		this.numberOfMutants = numMutants;
		this.numberOfSurvivors = numSurvivors;

		if (numMutants == 0) {
			this.coverage = null;
		} else {
			this.coverage = (double) (numMutants - numSurvivors) / (double) numMutants;
		}
	}

	public Double getCoverage() {
		return this.coverage;
	}

	public String getFqName() {
		return this.fqName;
	}

	public long getNumberOfMutants() {
		return this.numberOfMutants;
	}

	public long getNumberOfSurvivors() {
		return this.numberOfSurvivors;
	}

	public int compareTo(ClassLevelCoverage o) {
		return this.fqName.compareTo(o.fqName);
	}

	public String getShortName() {
		return this.shortName;
	}

	public int getNumTests() {
		return numTests;
	}

	public void getNumberOfTests(int numTests) {
		this.numTests = numTests;
	}

}
