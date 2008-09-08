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
public class PackageDetailBean {

	private String fqName;

	private long numberOfClasses;

	private long numberOfSurvivedMutations;

	private long numberOfDefeatedMutations;

	private double coverageLevel;

	public long getNumberOfClasses() {
		return numberOfClasses;
	}

	public void setNumberOfClasses(long numberOfClasses) {
		this.numberOfClasses = numberOfClasses;
	}

	public long getNumberOfSurvivedMutations() {
		return numberOfSurvivedMutations;
	}

	public void setNumberOfSurvivedMutations(long numberOfSurvivedMutations) {
		this.numberOfSurvivedMutations = numberOfSurvivedMutations;
	}

	public long getNumberOfDefeatedMutations() {
		return numberOfDefeatedMutations;
	}

	public void setNumberOfDefeatedMutations(long numberOfDefeatedMutations) {
		this.numberOfDefeatedMutations = numberOfDefeatedMutations;
	}

	public double getCoverageLevel() {
		return coverageLevel;
	}

	public void setCoverageLevel(double coverageLevel) {
		this.coverageLevel = coverageLevel;
	}

	public String getFqName() {
		return fqName;
	}

	public void setFqName(String fqName) {
		this.fqName = fqName;
	}

}
