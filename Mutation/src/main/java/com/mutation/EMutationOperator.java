package com.mutation;

import static com.mutation.EMutationInstruction.IFEQ;
import static com.mutation.EMutationInstruction.IFNE;
import static com.mutation.EMutationInstruction.IFNONNULL;
import static com.mutation.EMutationInstruction.IFNULL;
import static com.mutation.EMutationInstruction.IF_ACMPEQ;
import static com.mutation.EMutationInstruction.IF_ACMPNE;
import static com.mutation.EMutationInstruction.IF_ICMPEQ;
import static com.mutation.EMutationInstruction.IF_ICMPNE;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * This enum defines the possible mutation operators.
 * See http://ise.gmu.edu/~offutt/mujava/mutopsMethod.pdf for description of each
 * mutation operator.
 * 
 * @author mike
 * 
 */
public enum EMutationOperator {

	
	/**
	 * Arithmetic Operator Replacement
	 */
	AOR,
	/**
	 * Arithmetic Operator Insertion
	 */
	AOI,
	/**
	 * Arithmetic Operator Deletion
	 */
	AOD,
	/**
	 * Relational Operator Replacement
	 */
	ROR(IF_ICMPNE, IF_ICMPEQ, IF_ACMPNE, IF_ACMPEQ, IFEQ, IFNE, IFNULL, IFNONNULL),
	/**
	 * Conditional Operator Replacement
	 */
	COR,
	/**
	 * Conditional Operator Insertion
	 */
	COI,
	/**
	 * Conditional Operator Deletion
	 */
	COD,
	/**
	 * Shift Operator Replacement Replace shift operators with other shift
	 * operators.
	 */
	SOR,
	/**
	 * Logical Operator Replacement
	 */
	LOR,
	/**
	 * Logical Operator Insertion
	 */
	LOI,
	/**
	 * Logical Operator Delete
	 */
	LOD,
	/**
	 * Short-Cut Assignment Operator Replacement
	 */
	ASRS;
	
	
	private Set<EMutationInstruction> instructions;
	
	private EMutationOperator(EMutationInstruction ... instructions){
		this.instructions = new TreeSet<EMutationInstruction>( Arrays.asList(instructions) );
	}
	
	private EMutationOperator(){
		this.instructions = new TreeSet<EMutationInstruction>();
	}
	
	public Set<EMutationInstruction> getInstructions() {
		return instructions;
	}


}
