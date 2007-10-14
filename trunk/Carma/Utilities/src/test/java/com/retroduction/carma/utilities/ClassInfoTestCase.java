package com.retroduction.carma.utilities;

import junit.framework.TestCase;

public class ClassInfoTestCase extends TestCase {

	public void test_ClassNamingSplitting() {

		{
			ClassInfo info = new ClassInfo("a1");
			assertEquals("a1", info.getClassName());
			assertEquals("", info.getPackageName());
		}
		{
			ClassInfo info = new ClassInfo("123.a1");
			assertEquals("a1", info.getClassName());
			assertEquals("123", info.getPackageName());
		}
		{
			ClassInfo info = new ClassInfo("123.abc.a1");
			assertEquals("a1", info.getClassName());
			assertEquals("123.abc", info.getPackageName());
		}
		{
			ClassInfo info = new ClassInfo("a1");
			assertEquals("a1", info.getClassName());
			assertEquals("", info.getPackageName());
		}
		{
			ClassInfo info = new ClassInfo("");
			assertEquals("", info.getClassName());
			assertEquals("", info.getPackageName());
		}

	}

}
