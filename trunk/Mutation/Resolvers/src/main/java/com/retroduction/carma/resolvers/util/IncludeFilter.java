package com.retroduction.carma.resolvers.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncludeFilter {

	private String includePattern;

	private Pattern pattern;

	public IncludeFilter() {
		super();
	}
	
	public IncludeFilter(String includePattern) {
		super();
		setIncludePattern(includePattern);
	}

	public String getIncludePattern() {
		return includePattern;
	}

	public void setIncludePattern(String includePattern) {
		this.includePattern = includePattern;

		pattern = Pattern.compile(includePattern);
	}

	public boolean shouldBeIncluded(String fqClassName) {

		if (includePattern == null || includePattern.trim().equals("")) {
			setIncludePattern("(.*)");
		}

		Matcher matcher = pattern.matcher(fqClassName);

		return matcher.find();
	}
}
