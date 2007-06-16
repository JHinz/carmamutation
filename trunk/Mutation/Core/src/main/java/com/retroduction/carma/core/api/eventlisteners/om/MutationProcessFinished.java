package com.retroduction.carma.core.api.eventlisteners.om;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.utilities.ToStringUtils;

public class MutationProcessFinished implements IEvent {
	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
