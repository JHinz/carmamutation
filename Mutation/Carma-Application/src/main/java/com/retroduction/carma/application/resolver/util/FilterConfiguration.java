package com.retroduction.carma.application.resolver.util;

public class FilterConfiguration {

	private IncludeFilter includeFilter;

	private ExcludeFilter excludeFilter;

	public FilterConfiguration() {
		setIncludeFilter(new IncludeFilter());
		setExcludeFilter(new ExcludeFilter());
	}

	public ExcludeFilter getExcludeFilter() {
		return excludeFilter;
	}

	public void setExcludeFilter(ExcludeFilter classExcludeFilter) {
		this.excludeFilter = classExcludeFilter;
	}

	public IncludeFilter getIncludeFilter() {
		return includeFilter;
	}

	public void setIncludeFilter(IncludeFilter classIncludeFilter) {
		this.includeFilter = classIncludeFilter;
	}

}
