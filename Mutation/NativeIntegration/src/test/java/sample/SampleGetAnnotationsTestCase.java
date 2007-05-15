package sample;

import java.lang.annotation.Annotation;

import com.mutation.annotations.TestClassToClassMapping;

import junit.framework.TestCase;

public class SampleGetAnnotationsTestCase extends TestCase {

	public void testGetAnnotations() {
		Annotation[] annotations = SampleTestCase.class
				.getAnnotations();
		assertNotNull(annotations);
		assertTrue(annotations.length > 0);
		assertTrue(annotations[0] instanceof TestClassToClassMapping);
		assertEquals(1, ((TestClassToClassMapping)annotations[0]).classNames().length);
		assertEquals("sample.Sample", ((TestClassToClassMapping)annotations[0]).classNames()[0]);
	}
}
