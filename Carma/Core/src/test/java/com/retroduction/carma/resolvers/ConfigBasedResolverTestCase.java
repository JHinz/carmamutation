/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */

package com.retroduction.carma.resolvers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class ConfigBasedResolverTestCase extends TestCase {

	public void test_ReadConfig_MultiLineBaseTest() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.a.Class1=com.b.TestClass1,org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setTestClassesPath(null);

		Set<String> classes = new HashSet<String>();

		classes.add("com.a.Class1");
		classes.add("com.c.Class3");

		HashMap<String, Set<String>> result = resolver.parseInputConfiguration(inputConfig.toString(), classes);

		assertEquals(2, result.size());

		assertEquals(2, result.get("com.a.Class1").size());
		assertTrue(result.get("com.a.Class1").contains("com.b.TestClass1"));
		assertTrue(result.get("com.a.Class1").contains("org.AnotherTestClass2"));

		assertEquals(0, result.get("com.c.Class3").size());
	}

	public void test_ReadConfig_EmptyClassNameTest() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("=com.b.TestClass1,org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setTestClassesPath(null);

		Set<String> classes = new HashSet<String>();

		classes.add("com.a.Class1");
		classes.add("com.b.Class2");

		HashMap<String, Set<String>> result = resolver.parseInputConfiguration(inputConfig.toString(), classes);

		assertEquals(2, result.size());

		assertEquals(0, result.get("com.a.Class1").size());
		
		assertEquals(1, result.get("com.b.Class2").size());
		assertTrue(result.get("com.b.Class2").contains("com.b.TestClass2"));

	}

	public void test_ReadConfig_MissingEqualSeperatorTest() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.a.Class1com.b.TestClass1,org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setTestClassesPath(null);

		Set<String> classes = new HashSet<String>();

		classes.add("com.a.Class1");
		classes.add("com.b.Class2");

		HashMap<String, Set<String>> result = resolver.parseInputConfiguration(inputConfig.toString(), classes);

		assertEquals(2, result.size());

		assertEquals(0, result.get("com.a.Class1").size());
		
		assertEquals(1, result.get("com.b.Class2").size());
		assertTrue(result.get("com.b.Class2").contains("com.b.TestClass2"));
	}

	public void test_ReadConfig_DuplicateEqualSeperatorTest() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.a.Class1=com.b.TestClass1=org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setTestClassesPath(null);

		Set<String> classes = new HashSet<String>();

		classes.add("com.a.Class1");
		classes.add("com.b.Class2");

		HashMap<String, Set<String>> result = resolver.parseInputConfiguration(inputConfig.toString(), classes);

		assertEquals(2, result.size());

		assertEquals(0, result.get("com.a.Class1").size());

		assertEquals(1, result.get("com.b.Class2").size());
		assertTrue(result.get("com.b.Class2").contains("com.b.TestClass2"));
	}

	public void test_ReadConfig_SkipComments_CompleteCommentLine() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("#com.b.TestClass1=org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setTestClassesPath(null);

		Set<String> classes = new HashSet<String>();

		classes.add("com.a.Class1");
		classes.add("com.b.Class2");

		HashMap<String, Set<String>> result = resolver.parseInputConfiguration(inputConfig.toString(), classes);

		assertEquals(2, result.size());

		assertEquals(0, result.get("com.a.Class1").size());

		assertEquals(1, result.get("com.b.Class2").size());
		assertTrue(result.get("com.b.Class2").contains("com.b.TestClass2"));
	}

	public void test_ReadConfig_SkipComments_PartialCommentLine() throws MalformedURLException {

		StringBuffer inputConfig = new StringBuffer();

		inputConfig.append("com.a.Class1=com.b.TestClass1#,org.AnotherTestClass2\n");
		inputConfig.append("com.b.Class2=com.b.TestClass2\n");

		ConfigBasedResolver resolver = new ConfigBasedResolver();
		resolver.setTestClassesPath(null);

		Set<String> classes = new HashSet<String>();

		classes.add("com.a.Class1");
		classes.add("com.b.Class2");

		HashMap<String, Set<String>> result = resolver.parseInputConfiguration(inputConfig.toString(), classes);

		assertEquals(2, result.size());

		assertEquals(1, result.get("com.a.Class1").size());
		assertTrue(result.get("com.a.Class1").contains("com.b.TestClass1"));
		
		assertEquals(1, result.get("com.b.Class2").size());
		assertTrue(result.get("com.b.Class2").contains("com.b.TestClass2"));
	}

	public void test_ReadInputStream() throws IOException {

		ByteArrayInputStream inputStream = new ByteArrayInputStream("Test123\nTest234".getBytes());

		ConfigBasedResolver resolver = new ConfigBasedResolver();

		String result = resolver.readInputConfiguration(inputStream);

		assertEquals("Test123\nTest234\n", result);
	}
}
