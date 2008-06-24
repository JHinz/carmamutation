package com.retroduction.carma.transformer;

public class CRTEntry {

	private int startPos;
	private int endPos;
	private int startPC;
	private int endPC;
	private int flags;

	public CRTEntry() {
		super();
	}

	public CRTEntry(int startPos, int endPos, int startPC, int endPC, int flags) {
		super();
		this.startPos = startPos;
		this.endPos = endPos;
		this.startPC = startPC;
		this.endPC = endPC;
		this.flags = flags;
	}

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

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}

	public void setStartPC(int startPC) {
		this.startPC = startPC;
	}

	public void setEndPC(int endPC) {
		this.endPC = endPC;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getStartColumn() {
		return getStartPos() - (getStartPos() >> 10 << 10);
	}

	public int getEndColumn() {
		return getEndPos() - (getEndPos() >> 10 << 10);
	}

	public int getStartLine() {
		return getStartPos() >> 10;
	}

	public int getEndLine() {
		return getEndPos() >> 10;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append("StartPos: " + getStartPos() + " EndPos: " + getStartPos());

		sb.append(" StartLine: " + (getStartPos() >> 10) + " StartColumn: "
				+ (getStartPos() - ((getStartPos() >> 10) << 10)));

		sb.append(" EndLine: " + (getEndPos() >> 10) + " EndColumn: "
				+ (getEndPos() - ((getEndPos() >> 10) << 10)));

		sb.append(" StartPC: " + getStartPC() + " EndPC: " + getEndPC());

		return sb.toString();
	}

}
