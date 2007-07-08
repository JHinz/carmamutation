package sub2;

import com.retroduction.carma.annotations.TestClassToClassMapping;

@TestClassToClassMapping(classNames = { "sub1.SampleClassUsingAnnotation", "NonExistentClass" })
public class AnotherSampleClassUsingAnnotation {

}
