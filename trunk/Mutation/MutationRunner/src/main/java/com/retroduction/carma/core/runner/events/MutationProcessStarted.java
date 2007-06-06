package com.retroduction.carma.core.runner.events;

import java.util.List;

import com.retroduction.carma.core.runner.utililties.ToStringUtils;
import com.retroduction.carma.core.transform.AbstractTransitionGroup;

public class MutationProcessStarted implements IEvent {
	private List<AbstractTransitionGroup> transitionGroups;

	public List<AbstractTransitionGroup> getTransitionGroups() {
		return transitionGroups;
	}

	public MutationProcessStarted(List<AbstractTransitionGroup> transitionGroups) {
		super();
		this.transitionGroups = transitionGroups;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
