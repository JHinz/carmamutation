package com.mutation.resolver;

import com.mutation.IResolver;
import com.mutation.resolver.util.ExcludeFilter;

public abstract class AbstractFilteredResolver implements IResolver {

	private ExcludeFilter filter;

	public ExcludeFilter getFilter() {
		return filter;
	}

	public void setFilter(ExcludeFilter filter) {
		this.filter = filter;
	}

	public AbstractFilteredResolver() {
		super();
		setFilter(new ExcludeFilter());
	}
}
