package com.retroduction.carma.report.generator.reportobjects;

import java.util.Set;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRatio;

/**
 * aggregates information for a set of classes
 */
public class CoverageInfoAggregator {

	public CoverageInfo aggregate(String name, String shortName, Set<ClassUnderTest> classes){
		
		int sumMutants = 0;
		int sumSurvivors = 0;
		
		for(ClassUnderTest c : classes){
			MutationRatio ratio = c.getMutationRatio();
			sumMutants += ratio.getMutationCount();
			sumSurvivors += ratio.getSurvivorCount();
		}
		CoverageInfo info = new CoverageInfo(name, shortName, classes.size(), sumMutants, sumSurvivors);
		
		return info;
	}
}
