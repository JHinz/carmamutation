package com.retroduction.carma.report.generator.reportobjects;

import java.util.Set;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRatio;

/**
 * aggregates information for a set of classes
 */
public class Aggregator {
	public AggregatedClassInfo aggregate(Set<ClassUnderTest> classes){
		
		long sumMutants = 0;
		long sumSurvivors = 0;
		int numClasses = classes.size();
		
		for(ClassUnderTest c : classes){
			MutationRatio ratio = c.getMutationRatio();
			sumMutants += ratio.getMutationCount();
			sumSurvivors += ratio.getSurvivorCount();
		}
		
		AggregatedClassInfo info = new AggregatedClassInfo(sumMutants, sumSurvivors, numClasses);
		return info;
	}
}
