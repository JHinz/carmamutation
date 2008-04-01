/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
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
