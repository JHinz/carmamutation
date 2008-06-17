package com.retroduction.carma.transformer.asm;

import org.objectweb.asm.Label;

public class OffsetStartLabel extends Label {

	private int offset;

	public OffsetStartLabel(int offset) {
		super();
		this.offset = offset;
	}

	@Override
	public int getOffset() {
		return offset;
	}

}
