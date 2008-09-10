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



public class IF_ICMPLT_2_IF_ICMPGE_Transition extends ROR_Transition {

	public IF_ICMPLT_2_IF_ICMPGE_Transition() {
		super();
		this.sourceInstruction = Opcodes.IF_ICMPLT;
		this.targetInstruction = Opcodes.IF_ICMPGE;
	}

	public String getName() {
		return "IF_ICMPLT_to_IF_ICMPGE";
	}
}