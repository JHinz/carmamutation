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
package com.retroduction.carma.core.api.testrunners.om;

import java.util.Set;

import com.retroduction.carma.core.api.transitions.ITransition;
import com.retroduction.carma.core.api.transitions.ITransitionGroup;

/**
 * Specification of a mutant
 * 
 * @author mike
 * 
 */
public class Mutant {

	@Override
	public boolean equals(Object other) {
		if (other instanceof Mutant) {
			return this.byteCode.equals(((Mutant) other).byteCode);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.byteCode.hashCode();
	}

	@Override
	public String toString() {
		return this.name + " " + this.className + " " + this.transitionGroup;
	}

	private String name;

	private String className;

	private byte[] byteCode;

	private SourceCodeMapping sourceMapping;

	private ITransitionGroup transitionGroup;

	private ITransition transition;

	private boolean survived;

	private Set<String> executedTestsNames;

	private Set<String> killerTestNames;

	public Set<String> getKillerTestNames() {
		return this.killerTestNames;
	}

	public void setKillerTestNames(Set<String> killerTestNames) {
		this.killerTestNames = killerTestNames;
	}

	public Set<String> getExecutedTestsNames() {
		return this.executedTestsNames;
	}

	public void setExecutedTestsNames(Set<String> executedTestsNames) {
		this.executedTestsNames = executedTestsNames;
	}

	public boolean isSurvived() {
		return this.survived;
	}

	public void setSurvived(boolean survived) {
		this.survived = survived;
	}

	public byte[] getByteCode() {
		return this.byteCode;
	}

	public void setByteCode(byte[] byteCode) {
		this.byteCode = byteCode;
	}

	public SourceCodeMapping getSourceMapping() {
		return this.sourceMapping;
	}

	public void setSourceMapping(SourceCodeMapping sourceMapping) {
		this.sourceMapping = sourceMapping;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ITransitionGroup getTransitionGroup() {
		return this.transitionGroup;
	}

	public void setTransitionGroup(ITransitionGroup transitionGroup) {
		this.transitionGroup = transitionGroup;
	}

	public ITransition getTransition() {
		return this.transition;
	}

	public void setTransition(ITransition transition) {
		this.transition = transition;
	}

}
