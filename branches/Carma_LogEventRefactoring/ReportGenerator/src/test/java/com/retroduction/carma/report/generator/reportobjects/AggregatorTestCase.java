package com.retroduction.carma.report.generator.reportobjects;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.retroduction.carma.report.generator.reportobjects.AggregatedClassInfo;
import com.retroduction.carma.report.generator.reportobjects.Aggregator;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRatio;

public class AggregatorTestCase extends TestCase {

	public void testAggregate() {
		Aggregator cut = new Aggregator();

		{
			AggregatedClassInfo info = cut.aggregate(new HashSet<ClassUnderTest>());

			assertEquals(0, info.getNumClasses());
			assertEquals(0, info.getSumMutants());
			assertEquals(0, info.getSumSurvivors());
			assertEquals(1.0, info.getMutationCoverageMean());
		}

		{
			Set<ClassUnderTest> classes = new HashSet<ClassUnderTest>();
			{
				ClassUnderTest ct = new ClassUnderTest();
				MutationRatio ratio = new MutationRatio();
				ratio.setMutationCount(7);
				ratio.setSurvivorCount(3);
				ct.setMutationRatio(ratio);
				classes.add(ct);
			}
			
			{
				ClassUnderTest ct = new ClassUnderTest();
				MutationRatio ratio = new MutationRatio();
				ratio.setMutationCount(2);
				ratio.setSurvivorCount(1);
				ct.setMutationRatio(ratio);
				classes.add(ct);
			}

			AggregatedClassInfo info = cut.aggregate(classes);

			assertEquals(2, info.getNumClasses());
			assertEquals(9, info.getSumMutants());
			assertEquals(4, info.getSumSurvivors());
			assertEquals( (9.0 - 4.0)/9.0  , info.getMutationCoverageMean());
		}
	
	}
}
