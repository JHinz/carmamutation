package com.mutation.resolver;

import com.mutation.IResolver;
import com.mutation.resolver.util.ExcludeFilter;
import com.mutation.resolver.util.IncludeFilter;

public abstract class AbstractFilteredResolver implements IResolver {

	private ExcludeFilter testClassExcludeFilter;

	private ExcludeFilter classExcludeFilter;

	private IncludeFilter testClassIncludeFilter;

	private IncludeFilter classIncludeFilter;

	public IncludeFilter getClassIncludeFilter() {
		return classIncludeFilter;
	}

	public void setClassIncludeFilter(IncludeFilter classIncludeFilter) {
		this.classIncludeFilter = classIncludeFilter;
	}

	public IncludeFilter getTestClassIncludeFilter() {
		return testClassIncludeFilter;
	}

	public void setTestClassIncludeFilter(IncludeFilter testClassIncludeFilter) {
		this.testClassIncludeFilter = testClassIncludeFilter;
	}

	public ExcludeFilter getClassExcludeFilter() {
		return classExcludeFilter;
	}

	public void setClassExcludeFilter(ExcludeFilter classExcludeFilter) {
		this.classExcludeFilter = classExcludeFilter;
	}

	public ExcludeFilter getTestClassExcludeFilter() {
		return testClassExcludeFilter;
	}

	public void setTestClassExcludeFilter(ExcludeFilter filter) {
		this.testClassExcludeFilter = filter;
	}

	public AbstractFilteredResolver() {
		super();
		setTestClassExcludeFilter(new ExcludeFilter());
		setClassExcludeFilter(new ExcludeFilter());
		setTestClassIncludeFilter(new IncludeFilter());
		setClassIncludeFilter(new IncludeFilter());
	}
}
