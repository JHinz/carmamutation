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

public class ExcludeFilter {

	private Pattern pattern;

	private String fixedPatternString = "(.*\\$.*)";

	public ExcludeFilter() {
		super();
		this.setExcludePattern(null);
	}

	public ExcludeFilter(String excludePattern) {
		super();
		this.setExcludePattern(excludePattern);
	}

	public void setExcludePattern(String excludePattern) {

		if ((excludePattern == null) || excludePattern.trim().equals("")) {
			this.pattern = Pattern.compile(this.fixedPatternString);
		} else {
			this.pattern = Pattern.compile(excludePattern + "|" + this.fixedPatternString);
		}
	}

	public boolean shouldBeExcluded(String fqClassName) {

		Matcher matcher = this.pattern.matcher(fqClassName);

		return matcher.find();
	}
}
