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
