package com.retroduction.carma.transformer.asm.aor;

/**
 * Sample class with methods for testing several byte code transitions. Add new
 * sample methods at the end of the class as otherwise certain unit tests will
 * fail due to changed source code mappings.
 * 
 * Don't try to change any comments or imports as well due to this problem !
 * 
 * @author arau
 * 
 */
public class AOR_SampleClass {

	public int add3(int a) {

		int b = a + 3;

		return b;
	}

	public int add3TwoTimes(int a) {

		int c = a - 3;

		int b = a + 5;

		return b-c-a;
	}

}
