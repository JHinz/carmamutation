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

public class IFNE_2_IFEQ_Transition extends ROR_Transition {

	public IFNE_2_IFEQ_Transition() {
		super();
		this.sourceInstruction = Opcodes.IFNE;
		this.targetInstruction = Opcodes.IFEQ;
	}

	public String getName() {
		return "IFNE_to_IFEQ";
	}
}