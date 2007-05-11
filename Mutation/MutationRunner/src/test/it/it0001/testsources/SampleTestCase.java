package testsources;

import junit.framework.TestCase;
import sources.Sample;

public class SampleTestCase extends TestCase {

	public void testDecide() {
		Sample sample = new Sample();
		int result = sample.decide(1, 7);
		System.out.println("result: testDecide(1,7): " + result);
		assertEquals(1, result);
	}

	public void testDecide_aEqualsB() {
		Sample sample = new Sample();
		int result = sample.decide(4, 4);
		System.out.println("result: test_aEqualsB(4,4): " + result);
		assertEquals(7, result);
	}
}
