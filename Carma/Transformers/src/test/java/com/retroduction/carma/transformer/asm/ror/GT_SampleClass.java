/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.transformer.asm.ror;

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
public class GT_SampleClass {

	public int methodWith_IF_ICMPGT(int a) {

		if (a <= 1) { // translates to IF_ICMPGT on JDK5.0 (macosx)
			return 0;
		} else {
			return 1;
		}

	}
	
	public int methodWith_IFGT(int a) {

		if (a <= 0) { // translates to IFGT on JDK5.0 (macosx)
			return 0;
		} else {
			return 1;
		}

	}

}
