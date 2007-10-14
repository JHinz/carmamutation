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

	public int calculate(int a) {

		int b = a + 1;
		b = a - 1;
		b = a * 2;
		b = a / 2;

		return b;
	}

}
