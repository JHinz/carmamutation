package com.mutation.transform.asm.ror;

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
public class GE_SampleClass {

	public int methodWith_IF_ICMPGE(int a) {

		if (a < 1) { // translates to IF_ICMPGE on JDK5.0 (macosx)
			return 0;
		} else {
			return 1;
		}

	}
	
	public int methodWith_IFGE(int a) {

		if (a < 0) { // translates to IFGE on JDK5.0 (macosx)
			return 0;
		} else {
			return 1;
		}

	}

}
