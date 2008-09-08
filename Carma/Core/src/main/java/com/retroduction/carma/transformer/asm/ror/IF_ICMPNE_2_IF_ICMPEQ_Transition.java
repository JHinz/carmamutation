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


public class IF_ICMPNE_2_IF_ICMPEQ_Transition extends ROR_Transition {

	public IF_ICMPNE_2_IF_ICMPEQ_Transition() {
		super();
		this.sourceInstruction = Opcodes.IF_ICMPNE;
		this.targetInstruction = Opcodes.IF_ICMPEQ;
	}

	public String getName() {
		return "IF_ICMPNE_to_IF_ICMPEQ";
	}
}
