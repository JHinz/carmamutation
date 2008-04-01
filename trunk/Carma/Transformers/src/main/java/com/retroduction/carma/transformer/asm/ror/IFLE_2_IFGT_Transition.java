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

public class IFLE_2_IFGT_Transition extends ROR_Transition {

	public IFLE_2_IFGT_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFLE;
		this.targetInstruction = Opcodes.IFGT;
	}

	public String getName() {
		return "IFLE_to_IFGT";
	}
}
