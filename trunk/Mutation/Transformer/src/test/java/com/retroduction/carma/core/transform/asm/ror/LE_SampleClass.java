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
public class LE_SampleClass {

	public int methodWith_IF_ICMPLE(int a) {

		if (a > 1) { // translates to IF_ICMPLE on JDK5.0 (macosx)
			return 0;
		} else {
			return 1;
		}

	}
	
	public int methodWith_IFLE(int a) {

		if (a > 0) { // translates to IFLE on JDK5.0 (macosx)
			return 0;
		} else {
			return 1;
		}

	}

}
