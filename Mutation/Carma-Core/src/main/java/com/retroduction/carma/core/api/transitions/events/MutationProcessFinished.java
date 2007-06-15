package com.retroduction.carma.core.api.transitions.events;

import com.retroduction.carma.utilities.ToStringUtils;

public class MutationProcessFinished implements IEvent {
	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
