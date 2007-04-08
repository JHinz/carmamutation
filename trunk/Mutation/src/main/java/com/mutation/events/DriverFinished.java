package com.mutation.events;

import com.mutation.runner.events.IEvent;
import com.mutation.runner.utililties.ToStringUtils;

public class DriverFinished implements IEvent {
	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
