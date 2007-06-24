package com.retroduction.carma.resolvers.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcludeFilter {

	private Pattern pattern;

	private String fixedPatternString = "(.*\\$.*)";

	public ExcludeFilter() {
		super();
		setExcludePattern(null);
	}

	public ExcludeFilter(String excludePattern) {
		super();
		setExcludePattern(excludePattern);
	}

	public void setExcludePattern(String excludePattern) {

		if ((excludePattern == null) || excludePattern.trim().equals("")) {
			pattern = Pattern.compile(fixedPatternString);
		} else {
			pattern = Pattern.compile(excludePattern + "|" + fixedPatternString);
		}
	}

	public boolean shouldBeExcluded(String fqClassName) {

		Matcher matcher = pattern.matcher(fqClassName);

		return matcher.find();
	}
}
