package com.mutation.transform.asm.ror;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;

import com.mutation.runner.Mutant;
import com.mutation.runner.SourceCodeMapping;
import com.mutation.runner.events.IEventListener;
import com.mutation.transform.ITransition;

public abstract class ROR_Transition implements ITransition {

	// int IFEQ = 153
	// int IFNE = 154; // -
	// int IFLT = 155; // -
	// int IFGE = 156; // -
	// int IFGT = 157; // -
	// int IFLE = 158; // -
	// int IF_ICMPEQ = 159; // -
	// int IF_ICMPNE = 160; // -
	// int IF_ICMPLT = 161; // -
	// int IF_ICMPGE = 162; // -
	// int IF_ICMPGT = 163; // -
	// int IF_ICMPLE = 164; // -
	// int IF_ACMPEQ = 165; // -
	// int IF_ACMPNE = 166; // -

	protected int sourceInstruction;

	protected int targetInstruction;

	private IEventListener eventListener;

	public List<Mutant> applyTransitions(byte[] byteCode, IEventListener eventListener) {

		ClassNode classNode = new ClassNode();

		ClassReader reader = new ClassReader(byteCode);

		reader.accept(classNode, 0);

		List<Mutant> result = new ArrayList<Mutant>();

		for (MethodNode methodNode : (List<MethodNode>) classNode.methods) {

			Iterator<AbstractInsnNode> instructionInterator = methodNode.instructions.iterator();

			int currentInstructionLineNumber = 0;

			while (instructionInterator.hasNext()) {

				AbstractInsnNode node = instructionInterator.next();

				if (node instanceof LineNumberNode) {
					currentInstructionLineNumber = ((LineNumberNode) node).line;
					continue;
				}

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

						result.add(mutant);
						jumpNode.setOpcode(this.sourceInstruction);

					}

				}

			}

		}

		return result;
	}
}
