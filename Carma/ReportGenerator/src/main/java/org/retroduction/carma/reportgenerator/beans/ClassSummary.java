/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
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
