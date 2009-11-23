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
