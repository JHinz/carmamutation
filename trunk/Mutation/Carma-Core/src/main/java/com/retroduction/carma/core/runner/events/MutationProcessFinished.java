package com.retroduction.carma.core.runner.events;

import com.retroduction.carma.core.runner.utililties.ToStringUtils;

public class MutationProcessFinished implements IEvent {
	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
