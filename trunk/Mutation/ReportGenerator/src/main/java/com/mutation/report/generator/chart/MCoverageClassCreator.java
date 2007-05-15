package com.mutation.report.generator.chart;

import java.util.ArrayList;
import java.util.List;

import com.mutation.report.generator.reportobjects.ClassInfo;

public class MCoverageClassCreator {

	private double[] upperLimits;
	public MCoverageClassCreator(double[] upperLimits){
		this.upperLimits = upperLimits;
	}
	
	public List<DataClass> classifyMCoverage(List<ClassInfo> values){
		List<DataClass> classes = new ArrayList<DataClass>();
		for(double l : upperLimits){
			classes.add(new DataClass("<" +l));
		}
		
		for(ClassInfo ci : values){
			for(int i = 0; i<upperLimits.length; i++){
				double value = ci.getMCoverageRatio();
				if(value < upperLimits[i]){
					classes.get(i).addInstance(ci);
					break;
				}
			}
		}
		return classes;
	}
}
