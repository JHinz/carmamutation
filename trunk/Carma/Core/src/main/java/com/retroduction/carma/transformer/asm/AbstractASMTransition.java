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

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.transitions.ITransition;
import com.retroduction.carma.transformer.CRTEntry;
import com.retroduction.carma.transformer.CharacterRangeTable;

/**
 * 
 * Abstract mutation transformation processing class. Does the common traversing
 * logic over the byte code.
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

		reader.accept(classNode, new Attribute[] { new CharacterRangeTable() }, 0);

		List<Mutant> result = new ArrayList<Mutant>();

		int methodIndex = 0;

		for (MethodNode methodNode : (List<MethodNode>) classNode.methods) {

			Iterator<AbstractInsnNode> instructionIterator = methodNode.instructions.iterator();

			CharacterRangeTable crt = null;

			if (methodNode.attrs != null) {
				for (Attribute attr : (List<Attribute>) methodNode.attrs) {
					if (attr instanceof CharacterRangeTable) {
						crt = (CharacterRangeTable) attr;
					}
				}
			}

			CRTEntry crtEntry = new CRTEntry();

			while (instructionIterator.hasNext()) {

				AbstractInsnNode node = instructionIterator.next();

				if (node instanceof LineNumberNode) {
					if (crt == null) {
						crtEntry.setStartPos(((LineNumberNode) node).line << 10);
						crtEntry.setEndPos(((LineNumberNode) node).line << 10);
					}
					continue;
				}

				if ((node instanceof LabelNode)) {

					if (crt == null)
						continue;

					if (crt.getLabelOffsets().containsKey(((LabelNode) node).getLabel())) {
						crtEntry = crt.getLabelOffsets().get(((LabelNode) node).getLabel());
					}
					continue;
				}

				this.checkNode(classNode, methodNode, result, crtEntry, node);

			}
			methodIndex++;

		}

		return result;
	}

	protected abstract void checkNode(ClassNode classNode, MethodNode methodNode, List<Mutant> result,
			CRTEntry jcovInfo, AbstractInsnNode node);

}
