/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.annotations;

import java.lang.annotation.Annotation;

import junit.framework.TestCase;

public class SampleGetAnnotationsTestCase extends TestCase {

	public void testGetAnnotations() {
		Annotation[] annotations = SampleTestCase.class
				.getAnnotations();
		assertNotNull(annotations);
		assertTrue(annotations.length > 0);
		assertTrue(annotations[0] instanceof TestClassToClassMapping);
		assertEquals(1, ((TestClassToClassMapping)annotations[0]).classNames().length);
		assertEquals("sample.Sample", ((TestClassToClassMapping)annotations[0]).classNames()[0]);
	}
}
