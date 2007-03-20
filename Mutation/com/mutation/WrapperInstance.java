package com.mutation;

import com.mutation.test.ClassUnderTest;

public class WrapperInstance {

	public WrapperInstance() {
		super();
		ClassUnderTest cut = new ClassUnderTest();
		System.out.println("Result in wrapper instance: " + cut.get42());
	}

}
