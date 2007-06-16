package com.retroduction.carma.core.api.transitions.om;

import java.util.List;

import com.retroduction.carma.core.api.transitions.ITransitionGroup;


public class TransitionGroupConfig {

	private List<ITransitionGroup> transitionGroups;

	public List<ITransitionGroup> getTransitionGroups() {
		return transitionGroups;
	}

	public void setTransitionGroups(List<ITransitionGroup> transitionGroups) {
		this.transitionGroups = transitionGroups;
	}

}
