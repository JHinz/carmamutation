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

public class DADD_2_DSUB_Transition extends AOR_Transition {

	public DADD_2_DSUB_Transition() {
		super();
		this.sourceInstruction = Opcodes.DADD;
		this.targetInstruction = Opcodes.DSUB;
	}

	public String getName() {
		return "DADD_to_DSUB";
	}
}
