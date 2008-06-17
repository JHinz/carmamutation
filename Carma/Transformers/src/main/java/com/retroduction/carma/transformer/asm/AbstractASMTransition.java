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
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.transitions.ITransition;
import com.retroduction.carma.transformer.asm.CharacterRangeTable.Entry;

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

	public class JCovInfo {
		int startColumn = 0;
		int endColumn = 0;
		int startLine = 0;
		int endLine = 0;

		public int getStartColumn() {
			return startColumn;
		}

		public void setStartColumn(int startColumn) {
			this.startColumn = startColumn;
		}

		public int getEndColumn() {
			return endColumn;
		}

		public void setEndColumn(int endColumn) {
			this.endColumn = endColumn;
		}

		public int getStartLine() {
			return startLine;
		}

		public void setStartLine(int startLine) {
			this.startLine = startLine;
		}

		public int getEndLine() {
			return endLine;
		}

		public void setEndLine(int endLine) {
			this.endLine = endLine;
		}
	}

	public List<Mutant> applyTransitions(byte[] byteCode) {

		ClassNode classNode = new ClassNode();

		ClassReader reader = new ClassReader(byteCode);

		reader.accept(classNode, new Attribute[] { new CharacterRangeTable() }, 0);

		List<Mutant> result = new ArrayList<Mutant>();

		int methodIndex = 0;

		for (MethodNode methodNode : (List<MethodNode>) classNode.methods) {

			Iterator<AbstractInsnNode> instructionIterator = methodNode.instructions.iterator();

			CharacterRangeTable crt = new CharacterRangeTable();

			if (methodNode.attrs != null) {
				for (Attribute attr : (List<Attribute>) methodNode.attrs) {
					if (attr instanceof CharacterRangeTable) {
						crt = (CharacterRangeTable) attr;
					}
				}
			}

			int nodeCount = 0;

			JCovInfo jcovInfo = new JCovInfo();
			int jcovLookupIndex = 0;
			while (instructionIterator.hasNext()) {

				AbstractInsnNode node = instructionIterator.next();

				if ((node instanceof LineNumberNode)) {
					jcovInfo.setStartLine(((LineNumberNode) node).line);
					jcovInfo.setEndLine(((LineNumberNode) node).line);
					continue;
				}

				if ((node instanceof LabelNode)) {
					LabelNode labelNode = (LabelNode) node;
					Label label = labelNode.getLabel();
					if (label instanceof OffsetStartLabel) {
						jcovLookupIndex = label.getOffset();

					}
					continue;
				}

				if (crt.getEntries().size() > nodeCount) {
					Entry crtEntry = crt.getEntries().get(jcovLookupIndex);
					if (crtEntry != null)
						calculateLinesAndColumns(jcovInfo, crtEntry);
				}

				this.checkNode(classNode, methodNode, result, jcovInfo, node);

				nodeCount++;

			}
			methodIndex++;

		}

		return result;
	}

	private void calculateLinesAndColumns(JCovInfo jcovInfo, Entry crtEntry) {
		jcovInfo.setStartColumn(crtEntry.getStartPos() - (crtEntry.getStartPos() >> 10 << 10));
		jcovInfo.setEndColumn(crtEntry.getEndPos() - (crtEntry.getEndPos() >> 10 << 10));
		jcovInfo.setStartLine(crtEntry.getStartPos() >> 10);
		jcovInfo.setEndLine(crtEntry.getEndPos() >> 10);
	}

	protected abstract void checkNode(ClassNode classNode, MethodNode methodNode, List<Mutant> result,
			JCovInfo jcovInfo, AbstractInsnNode node);

}
