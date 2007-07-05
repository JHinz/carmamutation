package com.retroduction.carma.resolvers.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncludeFilter {

	private Pattern pattern;

	public IncludeFilter() {
		super();
		setIncludePattern(null);
	}

	public IncludeFilter(String includePattern) {
		super();
		setIncludePattern(includePattern);
	}

	public void setIncludePattern(String includePattern) {

		if (includePattern == null || includePattern.trim().equals("")) {
			includePattern = "(.*)";
		}

		pattern = Pattern.compile(includePattern);
	}

	public boolean shouldBeIncluded(String fqClassName) {

		Matcher matcher = pattern.matcher(fqClassName);

		return matcher.find();
	}
}
