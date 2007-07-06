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
			return byteCode.equals(((Mutant) other).byteCode);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return byteCode.hashCode();
	}

	@Override
	public String toString() {
		return name + " " + className + " " + transitionGroup;
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
		return killerTestNames;
	}

	public void setKillerTestNames(Set<String> killerTestNames) {
		this.killerTestNames = killerTestNames;
	}

	public Set<String> getExecutedTestsNames() {
		return executedTestsNames;
	}

	public void setExecutedTestsNames(Set<String> executedTestsNames) {
		this.executedTestsNames = executedTestsNames;
	}

	public boolean isSurvived() {
		return survived;
	}

	public void setSurvived(boolean survived) {
		this.survived = survived;
	}

	public byte[] getByteCode() {
		return byteCode;
	}

	public void setByteCode(byte[] byteCode) {
		this.byteCode = byteCode;
	}

	public SourceCodeMapping getSourceMapping() {
		return sourceMapping;
	}

	public void setSourceMapping(SourceCodeMapping sourceMapping) {
		this.sourceMapping = sourceMapping;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ITransitionGroup getTransitionGroup() {
		return transitionGroup;
	}

	public void setTransitionGroup(ITransitionGroup transitionGroup) {
		this.transitionGroup = transitionGroup;
	}

	public ITransition getTransition() {
		return transition;
	}

	public void setTransition(ITransition transition) {
		this.transition = transition;
	}

}
