package com.mutation.runner.events;

import java.util.List;

import com.mutation.runner.utililties.ToStringUtils;
import com.mutation.transform.ITransitionGroup;

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
