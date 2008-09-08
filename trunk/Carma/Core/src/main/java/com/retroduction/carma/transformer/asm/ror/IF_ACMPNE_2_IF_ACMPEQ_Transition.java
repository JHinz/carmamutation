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



public class IF_ACMPNE_2_IF_ACMPEQ_Transition extends ROR_Transition {

	public IF_ACMPNE_2_IF_ACMPEQ_Transition() {
		super();
		this.sourceInstruction = Opcodes.IF_ACMPNE;
		this.targetInstruction = Opcodes.IF_ACMPEQ;
	}
	
	public String getName() {
		return "IF_ACMPNE_to_IF_ACMPEQ";
	}
}
