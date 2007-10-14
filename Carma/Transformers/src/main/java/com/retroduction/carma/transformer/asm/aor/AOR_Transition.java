package com.retroduction.carma.transformer.asm.aor;

import java.util.List;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.testrunners.om.SourceCodeMapping;
import com.retroduction.carma.transformer.asm.AbstractASMTransition;

public abstract class AOR_Transition extends AbstractASMTransition {
	
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

	@Override
	protected void checkNode(ClassNode classNode, MethodNode methodNode, List<Mutant> result, int currentInstructionLineNumber,
			AbstractInsnNode node) {

		if (node instanceof InsnNode) {

			InsnNode sourceNode = (InsnNode) node;

			if (sourceNode.getOpcode() == this.sourceInstruction) {
				
				InsnNode targetNode = new InsnNode(this.targetInstruction);
								
				methodNode.instructions.set(sourceNode, targetNode);
				
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
				
				methodNode.instructions.set(targetNode, sourceNode);

			}

		}
	}
}
