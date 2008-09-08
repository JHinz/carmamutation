/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
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
