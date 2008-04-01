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


public class IF_ICMPGT_2_IF_ICMPLE_Transition extends ROR_Transition {

	public IF_ICMPGT_2_IF_ICMPLE_Transition() {
		super();
		this.sourceInstruction = Opcodes.IF_ICMPGT;
		this.targetInstruction = Opcodes.IF_ICMPLE;
	}

	public String getName() {
		return "IF_ICMPGT_to_IF_ICMPLE";
	}
}
