/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.core.api.eventlisteners.om;

import java.util.Set;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.utilities.ToStringUtils;

public class TestSetNotSane implements IEvent {

	private Set<String> testCaseName;

	public TestSetNotSane(Set<String> testCaseName) {
		super();
		this.testCaseName = testCaseName;
	}

	public Set<String> getTestCaseName() {
		return this.testCaseName;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
