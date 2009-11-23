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

package com.retroduction.carma.resolvers.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncludeFilter {

	private Pattern pattern;

	public IncludeFilter() {
		super();
		this.setIncludePattern(null);
	}

	public IncludeFilter(String includePattern) {
		super();
		this.setIncludePattern(includePattern);
	}

	public void setIncludePattern(String includePattern) {

		if (includePattern == null || includePattern.trim().equals("")) {
			includePattern = "(.*)";
		}

		this.pattern = Pattern.compile(includePattern);
	}

	public boolean shouldBeIncluded(String fqClassName) {

		Matcher matcher = this.pattern.matcher(fqClassName);

		return matcher.find();
	}
}
