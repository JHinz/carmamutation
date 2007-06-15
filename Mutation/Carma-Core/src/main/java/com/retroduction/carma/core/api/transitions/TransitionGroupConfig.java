package com.retroduction.carma.core.api.transitions;

import java.util.List;


public class TransitionGroupConfig {

	private List<ITransitionGroup> transitionGroups;

	public List<ITransitionGroup> getTransitionGroups() {
		return transitionGroups;
	}

	public void setTransitionGroups(List<ITransitionGroup> transitionGroups) {
		this.transitionGroups = transitionGroups;
	}

}
