/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */

package com.retroduction.carma.core.api.eventlisteners.om;

import java.util.Set;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.om.TestedClassInfo;
import com.retroduction.carma.utilities.ToStringUtils;

public class ClassesUnderTestResolved implements IEvent {

	private Set<TestedClassInfo> classUnderTestNames;

	public Set<TestedClassInfo> getClassUnderTestNames() {
		return this.classUnderTestNames;
	}

	public ClassesUnderTestResolved(Set<TestedClassInfo> classUnderTestNames) {
		super();
		this.classUnderTestNames = classUnderTestNames;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
}
