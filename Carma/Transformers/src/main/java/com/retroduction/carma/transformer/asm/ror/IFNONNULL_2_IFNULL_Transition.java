/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.transformer.asm.ror;

import org.objectweb.asm.Opcodes;

public class IFNONNULL_2_IFNULL_Transition extends ROR_Transition {

	public IFNONNULL_2_IFNULL_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFNONNULL;
		this.targetInstruction = Opcodes.IFNULL;
	}

	public String getName() {
		return "IFNONNULL_to_IFNULL";
	}
}
