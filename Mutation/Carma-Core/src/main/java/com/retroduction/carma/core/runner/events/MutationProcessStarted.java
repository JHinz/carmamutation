package com.retroduction.carma.core.runner.events;

import java.util.List;

import com.retroduction.carma.core.api.ITransitionGroup;
import com.retroduction.carma.core.runner.utililties.ToStringUtils;

public class MutationProcessStarted implements IEvent {
	private List<ITransitionGroup> transitionGroups;

	public List<ITransitionGroup> getTransitionGroups() {
		return transitionGroups;
	}

	public MutationProcessStarted(List<ITransitionGroup> transitionGroups) {
		super();
		this.transitionGroups = transitionGroups;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
