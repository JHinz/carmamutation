package com.retroduction.carma.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TestClassToClassMapping {

	public String[] classNames();

}
