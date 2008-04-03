package com.retroduction.carma.transformer.asm.ror;

/**
 * Sample class with methods for testing several byte code transitions.
 * Add new sample methods at the end of the class as otherwise many
 * unit tests will fail due to changed source code mappings.
 * 
 * Don't try to change any comments or imports as well due to this problem !
 * 
 * @author arau
 *
 */
public class Mixed_SampleClass {

	public int useMultiple_IF_ICMPEQ_And_IF_ICMPNE(int a) {

		if (a == 1) {
			if (a == 1) {
				if (a != 1) {
					return 1;
				} else {
					return 2;
				}
			} else {
				return 3;
			}
		} else {
			return 4;
		}

	}

}
