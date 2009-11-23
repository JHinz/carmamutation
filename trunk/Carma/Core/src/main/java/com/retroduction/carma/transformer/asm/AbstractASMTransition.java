/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
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
