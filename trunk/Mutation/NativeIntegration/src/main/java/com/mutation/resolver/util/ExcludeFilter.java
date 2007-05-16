package com.mutation.resolver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcludeFilter {

	private String excludePattern;

	private Pattern pattern;

	public String getExcludePattern() {
		return excludePattern;
	}

	public void setExcludePattern(String excludePattern) {
		this.excludePattern = excludePattern;

		pattern = Pattern.compile(excludePattern);
	}

	public boolean shouldBeExcluded(String fqClassName) {

		if (excludePattern == null || excludePattern.trim().equals("")) {
			setExcludePattern("\\(.*\\)");
		}

		Matcher matcher = pattern.matcher(fqClassName);

		return matcher.find();
	}
}
