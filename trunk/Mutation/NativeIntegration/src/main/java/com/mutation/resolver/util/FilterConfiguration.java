package com.mutation.resolver.util;

public class FilterConfiguration {

	private IncludeFilter classIncludeFilter;

	private IncludeFilter testClassIncludeFilter;

	private ExcludeFilter classExcludeFilter;

	private ExcludeFilter testClassExcludeFilter;

	public FilterConfiguration() {
		setClassIncludeFilter(new IncludeFilter());
		setTestClassIncludeFilter(new IncludeFilter());
		setClassExcludeFilter(new ExcludeFilter());
		setTestClassExcludeFilter(new ExcludeFilter());
	}

	public ExcludeFilter getClassExcludeFilter() {
		return classExcludeFilter;
	}

	public void setClassExcludeFilter(ExcludeFilter classExcludeFilter) {
		this.classExcludeFilter = classExcludeFilter;
	}

	public IncludeFilter getClassIncludeFilter() {
		return classIncludeFilter;
	}

	public void setClassIncludeFilter(IncludeFilter classIncludeFilter) {
		this.classIncludeFilter = classIncludeFilter;
	}

	public ExcludeFilter getTestClassExcludeFilter() {
		return testClassExcludeFilter;
	}

	public void setTestClassExcludeFilter(ExcludeFilter testClassExcludeFilter) {
		this.testClassExcludeFilter = testClassExcludeFilter;
	}

	public IncludeFilter getTestClassIncludeFilter() {
		return testClassIncludeFilter;
	}

	public void setTestClassIncludeFilter(IncludeFilter testClassIncludeFilter) {
		this.testClassIncludeFilter = testClassIncludeFilter;
	}

}
