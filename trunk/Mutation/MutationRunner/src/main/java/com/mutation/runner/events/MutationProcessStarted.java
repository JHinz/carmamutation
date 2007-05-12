package com.mutation.runner.events;

import java.util.List;

import com.mutation.runner.utililties.ToStringUtils;
import com.mutation.transform.AbstractTransitionGroup;

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
