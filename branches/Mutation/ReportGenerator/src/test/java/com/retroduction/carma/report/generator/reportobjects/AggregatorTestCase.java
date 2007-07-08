package com.retroduction.carma.report.generator.reportobjects;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.retroduction.carma.annotations.TestClassToClassMapping;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRatio;

@TestClassToClassMapping(classNames = { "com.retroduction.carma.report.generator.reportobjects.CoverageInfoAggregator" })

public class AggregatorTestCase extends TestCase {

	public void testAggregate() {
		CoverageInfoAggregator cut = new CoverageInfoAggregator();

		{
			CoverageInfo info = cut.aggregate("name.a", "x", new HashSet<ClassUnderTest>());

			assertEquals(0, info.getNumClasses());
			assertEquals(0, info.getNumMutants());
			assertEquals(0, info.getNumSurvivors());
			assertEquals(1.0, info.getCoverage());
			assertEquals("name.a", info.getFqName());
			assertEquals("x", info.getShortName());
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

			CoverageInfo info = cut.aggregate("some","x", classes);

			assertEquals(2, info.getNumClasses());
			assertEquals(9, info.getNumMutants());
			assertEquals(4, info.getNumSurvivors());
			assertEquals( (9.0 - 4.0)/9.0  , info.getCoverage());
		}
	
	}
}
