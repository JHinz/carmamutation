package com.retroduction.carma.core.api.events;

import com.retroduction.carma.utilities.ToStringUtils;

public class MutationProcessFinished implements IEvent {
	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
