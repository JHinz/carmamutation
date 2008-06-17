/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.transformer.asm.aor;

import org.objectweb.asm.Opcodes;

public class ISUB_2_IADD_Transition extends AOR_Transition {

	public ISUB_2_IADD_Transition() {
		super();
		this.sourceInstruction = Opcodes.ISUB;
		this.targetInstruction = Opcodes.IADD;
	}

	public String getName() {
		return "ISUB_to_IADD";
	}
}
