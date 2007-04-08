package sample;

import java.lang.annotation.Annotation;

import junit.framework.TestCase;

public class SampleGetAnnotationsTestCase extends TestCase {

	public void testGetAnnotations() {
		Annotation[] annotations = SampleTestCase.class
				.getAnnotations();
		assertNotNull(annotations);
		assertTrue(annotations.length > 0);
	}
}
