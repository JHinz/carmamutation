/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.chart;

/**
 * represents a class of data with a metric range, [lowerLimit, upperLimit)
 * @author mike
 *
 */
public class ClassRange {
	private double lowerLimit;
	private double upperLimit;
	private String title;
	public ClassRange(String title, double lowerLimit, double upperLimit) {
		super();
		this.title = title;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}
	public double getLowerLimit() {
		return this.lowerLimit;
	}
	public String getTitle() {
		return this.title;
	}
	public double getUpperLimit() {
		return this.upperLimit;
	}
	
	boolean isWithIn(double value){
		return value >= this.lowerLimit && value < this.upperLimit;
	}
}
