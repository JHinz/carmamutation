package com.mutation.runner.events;

import com.mutation.runner.utililties.ToStringUtils;

public class DriverFinished implements IEvent {
	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
