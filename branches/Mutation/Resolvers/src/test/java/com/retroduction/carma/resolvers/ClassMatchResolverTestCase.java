package com.retroduction.carma.resolvers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.retroduction.carma.core.api.testrunners.om.ClassDescription;

import junit.framework.TestCase;

public class ClassMatchResolverTestCase extends TestCase {

	public void test() {

		ClassMatchResolver resolver = new ClassMatchResolver();

		Set<ClassDescription> input = new HashSet<ClassDescription>();

		ClassDescription class1 = new ClassDescription();
		class1.setQualifiedClassName("com.retroduction.carma.test.Class1");
		input.add(class1);

		ClassDescription class2 = new ClassDescription();
		class2.setQualifiedClassName("com.retroduction.carma.test.Class2");
		input.add(class2);

		resolver.setTestNameSuffix("PussyCat");
		resolver.assignTestNames(input);

		assertEquals(2, input.size());

		HashMap<String, ClassDescription> classMap = new HashMap<String, ClassDescription>();

		for (ClassDescription classDescription : input)
			classMap.put(classDescription.getQualifiedClassName(), classDescription);

		{
			ClassDescription result = classMap.get("com.retroduction.carma.test.Class1");

			assertEquals(1, result.getAssociatedTestNames().size());
			assertTrue(result.getAssociatedTestNames().contains("com.retroduction.carma.test.Class1PussyCat"));
		}

		{
			ClassDescription result = classMap.get("com.retroduction.carma.test.Class2");

			assertEquals(1, result.getAssociatedTestNames().size());
			assertTrue(result.getAssociatedTestNames().contains("com.retroduction.carma.test.Class2PussyCat"));
		}
	}

	public void test_DefaultTestCaseSuffix() {

		ClassMatchResolver resolver = new ClassMatchResolver();

		Set<ClassDescription> input = new HashSet<ClassDescription>();

		ClassDescription class1 = new ClassDescription();
		class1.setQualifiedClassName("com.retroduction.carma.test.Class1");
		input.add(class1);

		resolver.assignTestNames(input);

		assertEquals(1, input.size());

		HashMap<String, ClassDescription> classMap = new HashMap<String, ClassDescription>();

		for (ClassDescription classDescription : input)
			classMap.put(classDescription.getQualifiedClassName(), classDescription);

		{
			ClassDescription result = classMap.get("com.retroduction.carma.test.Class1");

			assertEquals(1, result.getAssociatedTestNames().size());
			assertTrue(result.getAssociatedTestNames().contains("com.retroduction.carma.test.Class1Test"));
		}

	}

}
