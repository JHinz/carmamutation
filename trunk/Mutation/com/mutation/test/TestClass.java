package com.mutation.test;

import junit.framework.TestCase;

public class TestClass extends TestCase {

	public void testGet42() {

		assertEquals(41, new ClassUnderTest().get42());

	}
}
