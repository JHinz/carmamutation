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
		int result = sample.decide(1, 7);
		log.info("result: testDecide(1,7): " + result);
		assertEquals(1, result);
	}

	public void testDecide_aEqualsB() {
		Sample sample = new Sample();
		int result = sample.decide(4, 4);
		log.info("result: test_aEqualsB(4,4): " + result);
		assertEquals(7, result);
	}
}
