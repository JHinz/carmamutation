/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.transformer.asm;

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

/**
 * 
 * Abstract mutation transformation processing class. Does the common traversing logic
 * over the byte code.
 * 
 * @author arau
 * 
 * TODO: arau: should be implemented using delegation instead of inheritance.
 *
 */
public abstract class AbstractASMTransition implements ITransition {

	public List<Mutant> applyTransitions(byte[] byteCode) {

		ClassNode classNode = new ClassNode();

		ClassReader reader = new ClassReader(byteCode);

		reader.accept(classNode, 0);

		List<Mutant> result = new ArrayList<Mutant>();

		for (MethodNode methodNode : (List<MethodNode>) classNode.methods) {

			Iterator<AbstractInsnNode> instructionIterator = methodNode.instructions.iterator();

			int currentInstructionLineNumber = 0;

			while (instructionIterator.hasNext()) {

				AbstractInsnNode node = instructionIterator.next();

				if (node instanceof LineNumberNode) {
					currentInstructionLineNumber = ((LineNumberNode) node).line;
					continue;
				}

				this.checkNode(classNode, methodNode, result, currentInstructionLineNumber, node);

			}

		}

		return result;
	}

	protected abstract void checkNode(ClassNode classNode, MethodNode methodNode, List<Mutant> result, int currentInstructionLineNumber,
			AbstractInsnNode node);

}
