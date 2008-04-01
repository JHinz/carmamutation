/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.reportobjects;

import java.util.Collection;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRatio;

/**
 * aggregates information for a set of classes
 */
public class CoverageInfoAggregator {

	public CoverageInfo aggregate(String name, String shortName, Collection<ClassUnderTest> classes){
		
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
