package com.retroduction.carma.report.generator.chart;

import com.retroduction.carma.report.generator.chart.ClassRange;

import junit.framework.TestCase;

public class ClassRangeTestCase extends TestCase {

	public void testIsWithin(){
		ClassRange range = new ClassRange("My", 1.0, 10.0);
		
		assertFalse(range.isWithIn(0.999999));
		assertTrue(range.isWithIn(1.0));
		assertTrue(range.isWithIn(9.9999999));
		assertFalse(range.isWithIn(10.0));
	}
}