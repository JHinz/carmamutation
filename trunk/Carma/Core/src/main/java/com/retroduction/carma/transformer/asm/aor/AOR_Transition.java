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

package com.retroduction.carma.transformer.asm.aor;

import java.util.List;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.testrunners.om.SourceCodeMapping;
import com.retroduction.carma.transformer.CRTEntry;
import com.retroduction.carma.transformer.asm.AbstractASMTransition;

public abstract class AOR_Transition extends AbstractASMTransition {

	/*
	 * 2 Integer byte code comparisons implemented:
	 * 
	 * EQ NE LT LE GT GE
	 * 
	 * EQ -- impl -- -- -- --
	 * 
	 * NE impl -- -- -- -- --
	 * 
	 * LT -- -- -- -- -- impl
	 * 
	 * LE -- -- -- -- impl --
	 * 
	 * GT -- -- -- impl -- --
	 * 
	 * GE -- -- impl -- -- --
	 * 
	 */

	/*
	 * Integer/Const value byte code comparisons implemented:
	 * 
	 * EQ NE LT LE GT GE
	 * 
	 * EQ -- impl -- -- -- --
	 * 
	 * NE impl -- -- -- -- --
	 * 
	 * LT -- -- -- -- -- impl
	 * 
	 * LE -- -- -- -- impl --
	 * 
	 * GT -- -- -- impl -- --
	 * 
	 * GE -- -- impl -- -- --
	 * 
	 */

	/*
	 * Reference byte code comparisons implemented:
	 * 
	 * EQ NE
	 * 
	 * EQ -- impl
	 * 
	 * NE impl --
	 * 
	 */

	protected int sourceInstruction;

	protected int targetInstruction;

	@Override
	protected void checkNode(ClassNode classNode, MethodNode methodNode, List<Mutant> result, CRTEntry jcovInfo,
			AbstractInsnNode node) {

		if (node instanceof InsnNode) {

			InsnNode sourceNode = (InsnNode) node;

			if (sourceNode.getOpcode() == this.sourceInstruction) {

				InsnNode targetNode = new InsnNode(this.targetInstruction);

				methodNode.instructions.set(sourceNode, targetNode);

				ClassWriter writer = new ClassWriter(0);
				classNode.accept(writer);

				SourceCodeMapping sourceMapping = new SourceCodeMapping();
				sourceMapping.setLineStart(jcovInfo.getStartLine());
				sourceMapping.setLineEnd(jcovInfo.getEndLine());
				sourceMapping.setColumnStart(jcovInfo.getStartColumn());
				sourceMapping.setColumnEnd(jcovInfo.getEndColumn());

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
