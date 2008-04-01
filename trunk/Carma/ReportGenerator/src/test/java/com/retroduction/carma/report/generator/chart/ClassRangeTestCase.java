/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.chart;

import junit.framework.TestCase;

import com.retroduction.carma.annotations.TestClassToClassMapping;

@TestClassToClassMapping(classNames = { "com.retroduction.carma.report.generator.chart.ClassRange" })
public class ClassRangeTestCase extends TestCase {

	public void testIsWithin(){
		ClassRange range = new ClassRange("My", 1.0, 10.0);
		
		assertFalse(range.isWithIn(0.999999));
		assertTrue(range.isWithIn(1.0));
		assertTrue(range.isWithIn(9.9999999));
		assertFalse(range.isWithIn(10.0));
	}
}
