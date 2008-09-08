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

/**
 * @author arau
 *
 */
public class ClassSummary {

	private String fqClassName;
	private double surviverRatio;
	private int mutantCount;
	private int survivorCount;
	private List<String> tests;

	public ClassSummary(String fqClassName, int numMutants, int numSurvivors, List<String> tests) {
		super();
		this.fqClassName = fqClassName;
		this.mutantCount = numMutants;
		this.survivorCount = numSurvivors;
		this.tests = tests;
		this.surviverRatio = numMutants == 0 ? 0.0 : (double) numSurvivors / (double) numMutants;
	}

	public String getFqClassName() {
		return this.fqClassName;
	}

	public int getMutantCount() {
		return this.mutantCount;
	}

	public int getSurvivorCount() {
		return this.survivorCount;
	}

	public double getSurviverRatio() {
		return this.surviverRatio;
	}

	public double getCoverageRatio() {
		return 1.0 - this.surviverRatio;
	}

	public List<String> getTests() {
		return this.tests;
	}
}
