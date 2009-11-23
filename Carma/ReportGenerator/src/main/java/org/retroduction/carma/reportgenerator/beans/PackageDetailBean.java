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
public class PackageDetailBean {

	private String fqName;

	private long numberOfClasses;

	private long numberOfSurvivedMutations;

	private long numberOfDefeatedMutations;

	private Double coverageLevel;

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

	public Double getCoverageLevel() {
		return coverageLevel;
	}

	public void setCoverageLevel(Double coverageLevel) {
		this.coverageLevel = coverageLevel;
	}

	public String getFqName() {
		return fqName;
	}

	public void setFqName(String fqName) {
		this.fqName = fqName;
	}

}
