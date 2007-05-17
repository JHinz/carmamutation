package com.mutation.report.generator.chart;

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
		return lowerLimit;
	}
	public String getTitle() {
		return title;
	}
	public double getUpperLimit() {
		return upperLimit;
	}
	
	boolean isWithIn(double value){
		return value >= lowerLimit && value < upperLimit;
	}
}
