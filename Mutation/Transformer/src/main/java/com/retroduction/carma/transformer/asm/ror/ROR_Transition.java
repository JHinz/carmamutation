package com.retroduction.carma.transformer.asm.ror;

import java.util.List;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.JumpInsnNode;

import com.retroduction.carma.core.api.testrunners.Mutant;
import com.retroduction.carma.core.api.testrunners.SourceCodeMapping;

public abstract class ROR_Transition extends AbstractASMTransition {
	
	/*
	 * 2 Integer byte code comparisons implemented:
	 * 
	 *      EQ   NE   LT   LE   GT   GE
	 * 
	 * EQ   --  impl  --   --   --   --
	 * 
	 * NE   impl --   --   --   --   --
	 * 
	 * LT   --   --   --   --   --  impl
	 * 
	 * LE   --   --   --   --   impl --
	 * 
	 * GT   --   --   --  impl  --   --
	 * 
	 * GE   --   --  impl  --   --   --
	 * 
	 */
	
	/*
	 * Integer/Const value byte code comparisons implemented:
	 * 
	 *      EQ   NE   LT   LE   GT   GE
	 * 
	 * EQ   --  impl  --   --   --   --
	 * 
	 * NE   impl --   --   --   --   --
	 * 
	 * LT   --   --   --   --   --  impl
	 * 
	 * LE   --   --   --   --   impl --
	 * 
	 * GT   --   --   --  impl  --   --
	 * 
	 * GE   --   --  impl  --   --   --
	 * 
	 */
	
	/*
	 * Reference byte code comparisons implemented:
	 * 
	 *      EQ   NE   
	 * 
	 * EQ   --  impl   
	 * 
	 * NE  impl  --  
	 * 
	 */

	protected int sourceInstruction;

	protected int targetInstruction;

	protected void checkNode(ClassNode classNode, List<Mutant> result, int currentInstructionLineNumber,
			AbstractInsnNode node) {

		if (node instanceof JumpInsnNode) {

			JumpInsnNode jumpNode = (JumpInsnNode) node;

			if (jumpNode.getOpcode() == this.sourceInstruction) {

				jumpNode.setOpcode(this.targetInstruction);

				ClassWriter writer = new ClassWriter(0);
				classNode.accept(writer);

				SourceCodeMapping sourceMapping = new SourceCodeMapping();
				sourceMapping.setLineNo(currentInstructionLineNumber);

				Mutant mutant = new Mutant();
				mutant.setByteCode(writer.toByteArray());
				mutant.setSourceMapping(sourceMapping);
				mutant.setSurvived(true);
				mutant.setTransition(this);

				result.add(mutant);
				jumpNode.setOpcode(this.sourceInstruction);

			}

		}
	}
}
