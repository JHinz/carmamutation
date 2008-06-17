package com.retroduction.carma.transformer.asm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;

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

	public class Entry {

		private int startPos;
		private int endPos;
		private int startPC;
		private int endPC;
		private int flags;

		public int getStartPos() {
			return startPos;
		}

		public int getEndPos() {
			return endPos;
		}

		public int getStartPC() {
			return startPC;
		}

		public int getEndPC() {
			return endPC;
		}

		private void setStartPos(int startPos) {
			this.startPos = startPos;
		}

		private void setEndPos(int endPos) {
			this.endPos = endPos;
		}

		private void setStartPC(int startPC) {
			this.startPC = startPC;
		}

		private void setEndPC(int endPC) {
			this.endPC = endPC;
		}

		private int getFlags() {
			return flags;
		}

		private void setFlags(int flags) {
			this.flags = flags;
		}

	}

	private Vector<Entry> entries;

	public Vector<Entry> getEntries() {
		return entries;
	}

	public CharacterRangeTable() {
		super("CharacterRangeTable");
		this.entries = new Vector<Entry>();
		this.entries.setSize(10000);
	}

	@Override
	public boolean isUnknown() {
		return false;
	}

	@Override
	protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {

		CharacterRangeTable crt = new CharacterRangeTable();

		ByteBuffer bb = ByteBuffer.wrap(cr.b, off, len);
		bb.order(ByteOrder.BIG_ENDIAN);

		int characterRangeTableLength = bb.getChar();

		for (int i = 0; i < characterRangeTableLength; i++) {

			Entry entry = new Entry();
			entry.setStartPC(bb.getChar());
			entry.setEndPC(bb.getChar());
			entry.setStartPos(bb.getInt());
			entry.setEndPos(bb.getInt() + 1);
			entry.setFlags(bb.getChar());

			for (int x = entry.getStartPC(); x <= entry.getEndPC(); x++) {

				if (crt.entries.get(x) == null)
					crt.entries.set(x, entry);
			}

			// if (!sortedEntries.containsKey(entry.getStartPC()))
			// && (((entry.getFlags() ^ CRT_ASSIGNMENT) == 0
			// || (entry.getFlags() ^ CRT_FLOW_CONTROLLER) == 0
			// || (entry.getFlags() ^ CRT_STATEMENT) == 0
			// || (entry.getFlags() ^ CRT_BLOCK) == 0 ||
			// (entry.getFlags() ^ CRT_FLOW_TARGET) == 0)))
			// {
			//				
			// while (crt.entries.size() <= entry.getEndPC()) {
			// crt.entries.add(entry);
			// }
			//
			// sortedEntries.put(entry.getStartPC(), entry);
			//
			// }
		}

		for (Entry entry : crt.entries) {

			if (entry == null)
				continue;

			OffsetStartLabel startLabel = new OffsetStartLabel(entry.startPC);
			labels[entry.getStartPC()] = startLabel;

		}

		return crt;
	}

	@Override
	protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
		return new ByteVector().putShort(cw.newUTF8(""));
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (Entry entry : entries) {
			if (entry != null) {
				sb.append("StartPos: " + entry.getStartPos() + " EndPos: " + entry.getStartPos());

				sb.append(" StartLine: " + (entry.getStartPos() >> 10) + " StartColumn: "
						+ (entry.getStartPos() - ((entry.getStartPos() >> 10) << 10)));

				sb.append(" EndLine: " + (entry.getEndPos() >> 10) + " EndColumn: "
						+ (entry.getEndPos() - ((entry.getEndPos() >> 10) << 10)));

				sb.append(" StartPC: " + entry.getStartPC() + " EndPC: " + entry.getEndPC());
				sb.append("\n");
			}
		}

		return sb.toString();
	}
}
