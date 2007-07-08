package com.retroduction.carma.resolvers.util;

public class FilterConfiguration {

	private IncludeFilter includeFilter;

	private ExcludeFilter excludeFilter;

	public FilterConfiguration() {
		this.setIncludeFilter(new IncludeFilter());
		this.setExcludeFilter(new ExcludeFilter());
	}

	public ExcludeFilter getExcludeFilter() {
		return this.excludeFilter;
	}

	public void setExcludeFilter(ExcludeFilter classExcludeFilter) {
		this.excludeFilter = classExcludeFilter;
	}

	public IncludeFilter getIncludeFilter() {
		return this.includeFilter;
	}

	public void setIncludeFilter(IncludeFilter classIncludeFilter) {
		this.includeFilter = classIncludeFilter;
	}

}
