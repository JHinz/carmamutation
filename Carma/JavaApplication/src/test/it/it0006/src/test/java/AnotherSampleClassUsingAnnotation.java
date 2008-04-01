/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
import com.mutation.annotations.TestClassToClassMapping;

import junit.framework.TestCase;

@TestClassToClassMapping(classNames = { "SampleClassUsingAnnotation" })
public class AnotherSampleClassUsingAnnotation extends TestCase {

	public void test() {
		assertTrue(true);
	}

}
