package com.retroduction.carma.core.api.eventlisteners.om;

import java.util.Set;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.api.transitions.ITransitionGroup;
import com.retroduction.carma.utilities.ToStringUtils;

public class MutationProcessStarted implements IEvent {

	private Set<ITransitionGroup> transitionGroups;

	public Set<ITransitionGroup> getTransitionGroups() {
		return transitionGroups;
	}

	public MutationProcessStarted(Set<ITransitionGroup> transitionGroups) {
		super();
		this.transitionGroups = transitionGroups;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
