/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
import junit.framework.TestCase;

public class SampleTestCase extends TestCase {

	public void testDecide() {
		Sample sample = new Sample();
		int result = sample.decide(true, true);
		assertEquals(1, result);
	}

	public void testDecide_aEqualsB() {
		Sample sample = new Sample();
		int result = sample.decide(true, false);
		assertEquals(2, result);
	}
}
