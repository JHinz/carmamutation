package com.retroduction.carma.report.generator.chart;

import java.util.ArrayList;
import java.util.List;

import com.retroduction.carma.report.generator.reportobjects.ClassInfo;

public class MCoverageClassCreator {

	private List<DataClass> classes;

	public MCoverageClassCreator(double[] upperLimits) {
		this.classes = new ArrayList<DataClass>();
		double lower = Double.MIN_VALUE;
		for (double l : upperLimits) {
			ClassRange range = new ClassRange("[" + lower + "," + l + ")", lower, l);
			lower = l;
			this.classes.add(new DataClass(range));
		}
	}

	public MCoverageClassCreator(List<ClassRange> ranges) {
		this.classes = new ArrayList<DataClass>();
		for (ClassRange range : ranges) {
			this.classes.add(new DataClass(range));
		}
	}	
	
	public List<DataClass> classifyMCoverage(List<ClassInfo> values) {

		for (ClassInfo ci : values) {
			for (DataClass c : this.classes) {
				double value = ci.getMCoverageRatio();
				if (c.getRange().isWithIn(value)) {
					c.addInstance(ci);
					break;
				}
			}
		}
		return this.classes;
	}
}
