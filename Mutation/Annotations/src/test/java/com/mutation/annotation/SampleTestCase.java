package com.mutation.annotation;

import junit.framework.TestCase;

import com.mutation.annotations.TestClassToClassMapping;

@TestClassToClassMapping(classNames = { "sample.Sample" })
public class SampleTestCase extends TestCase {

	public void testDecide() {
		Sample sample = new Sample();
		assertEquals(1, sample.decide(1, 7));
	}

	public void testDecide_aEqualsB() {
		Sample sample = new Sample();
		assertEquals(7, sample.decide(4, 4));
	}
}
