/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package testsources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.core.runner.utililties.ClassLoaderInfo;

import junit.framework.TestCase;
import sources.Sample;

public class SampleTestCase extends TestCase {

	private Log log = LogFactory.getLog(SampleTestCase.class);

	public void testDecide() {
		Sample sample = new Sample();
		int result = sample.decide(1);
		log.info("result: testDecide(1): " + result);
		assertEquals(7, result);
	}
}
