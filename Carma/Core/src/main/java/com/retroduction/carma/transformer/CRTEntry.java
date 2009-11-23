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
