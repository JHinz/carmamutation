package com.mutation.events;

import com.mutation.util.ToStringUtils;

public class DriverFinished implements IEvent {
	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
