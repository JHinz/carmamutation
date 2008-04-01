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

public class IFLT_2_IFGE_Transition extends ROR_Transition {

	public IFLT_2_IFGE_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFLT;
		this.targetInstruction = Opcodes.IFGE;
	}

	public String getName() {
		return "IFLT_to_IFGE";
	}
}
