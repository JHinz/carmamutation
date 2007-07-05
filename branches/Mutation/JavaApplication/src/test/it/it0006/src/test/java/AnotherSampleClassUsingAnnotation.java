import com.mutation.annotations.TestClassToClassMapping;

import junit.framework.TestCase;

@TestClassToClassMapping(classNames = { "SampleClassUsingAnnotation" })
public class AnotherSampleClassUsingAnnotation extends TestCase {

	public void test() {
		assertTrue(true);
	}

}
