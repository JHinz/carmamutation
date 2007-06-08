package com.retroduction.carma.application.testresolver;

import junit.framework.TestCase;

import com.retroduction.carma.application.resolver.OneTestPerClassResolver;

public class OneTestPerClassResolverTestCase extends TestCase {

	public void testDetermineTests(){
		OneTestPerClassResolver resolver = new OneTestPerClassResolver();
		resolver.setTestCaseSuffix("xxX");
		assertEquals("com.MyClassxxX",resolver.determineTests("com.MyClass").iterator().next());
		assertEquals("xxX",resolver.determineTests("").iterator().next());
	}
}
