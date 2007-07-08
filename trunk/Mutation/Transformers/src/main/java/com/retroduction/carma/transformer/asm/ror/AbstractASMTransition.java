package com.retroduction.carma.transformer.asm.ror;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.transitions.ITransition;

 abstract class AbstractASMTransition implements ITransition {

	public List<Mutant> applyTransitions(byte[] byteCode) {

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

				this.checkNode(classNode, result, currentInstructionLineNumber, node);

			}

		}

		return result;
	}

	protected abstract void checkNode(ClassNode classNode, List<Mutant> result, int currentInstructionLineNumber,
			AbstractInsnNode node);

}
