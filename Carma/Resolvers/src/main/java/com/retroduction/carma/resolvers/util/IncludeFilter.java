/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
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
