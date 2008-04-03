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

