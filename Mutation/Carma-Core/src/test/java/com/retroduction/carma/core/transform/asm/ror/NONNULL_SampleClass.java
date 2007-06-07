package com.retroduction.carma.core.transform.asm.ror;

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
public class NONNULL_SampleClass {

	public int methodWith_IFNONNULL(Object a) {

		if (a == null) { // translates to IFNONNULL on JDK5.0 (macosx)
			return 0;
		} else {
			return 1;
		}

	}

}
