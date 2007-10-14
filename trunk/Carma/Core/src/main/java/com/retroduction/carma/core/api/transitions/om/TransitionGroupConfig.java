package com.retroduction.carma.core.api.transitions.om;

import java.util.Set;

import com.retroduction.carma.core.api.transitions.ITransitionGroup;

public class TransitionGroupConfig {

	private Set<ITransitionGroup> transitionGroups;

	public Set<ITransitionGroup> getTransitionGroups() {
		return this.transitionGroups;
	}

	public void setTransitionGroups(Set<ITransitionGroup> transitionGroups) {
		this.transitionGroups = transitionGroups;
	}

}
