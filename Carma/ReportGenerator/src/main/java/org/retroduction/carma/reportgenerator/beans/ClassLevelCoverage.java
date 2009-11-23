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
