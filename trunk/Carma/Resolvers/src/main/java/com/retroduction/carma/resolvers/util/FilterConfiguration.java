/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
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
