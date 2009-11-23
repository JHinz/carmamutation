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
package com.retroduction.carma.transformer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

public class CharacterRangeTable extends Attribute {

	// private static final int CRT_STATEMENT = 0x0001;
	// private static final int CRT_BLOCK = 0x0002;
	// private static final int CRT_ASSIGNMENT = 0x0004;
	// private static final int CRT_FLOW_CONTROLLER = 0x0008;
	// private static final int CRT_FLOW_TARGET = 0x0010;
	// private static final int CRT_INVOKE = 0x0020;
	// private static final int CRT_CREATE = 0x0040;
	// private static final int CRT_BRANCH_TRUE = 0x0080;
	// private static final int CRT_BRANCH_FALSE = 0x100;

	private HashMap<Label, CRTEntry> labelOffsets;

	public HashMap<Label, CRTEntry> getLabelOffsets() {
		return labelOffsets;
	}

	public CharacterRangeTable() {
		super("CharacterRangeTable");
	}

	@Override
	public boolean isUnknown() {
		return false;
	}

	@Override
	public boolean isCodeAttribute() {
		return true;
	}

	@Override
	protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {

		CharacterRangeTable crt = new CharacterRangeTable();
		
		ByteBuffer bb = ByteBuffer.wrap(cr.b, off, len);
		bb.order(ByteOrder.BIG_ENDIAN);

		int characterRangeTableLength = bb.getChar();

		CRTLookup lookup = new CRTLookup();

		List<CRTEntry> entries = new ArrayList<CRTEntry>();

		for (int i = 0; i < characterRangeTableLength; i++) {

			CRTEntry entry = new CRTEntry();
			entry.setStartPC(bb.getChar());
			entry.setEndPC(bb.getChar());
			entry.setStartPos(bb.getInt());
			entry.setEndPos(bb.getInt() + 1);
			entry.setFlags(bb.getChar());

			entries.add(entry);
			lookup.addCRTEntry(entry);

		}

		crt.labelOffsets = new HashMap<Label, CRTEntry>();

		for (Map.Entry<Integer, CRTEntry> entry : lookup.getEntries().entrySet()) {

			int startPC = entry.getValue().getStartPC();
			int endPC = entry.getValue().getEndPC();

			for (int pc = startPC; pc <= endPC; pc++) {

				if (labels[pc] == null) {
					labels[pc] = new Label();
				}
				crt.labelOffsets.put(labels[pc], entry.getValue());
			}
		}

		return crt;
	}

	@Override
	protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
		return new ByteVector();
	}

}
