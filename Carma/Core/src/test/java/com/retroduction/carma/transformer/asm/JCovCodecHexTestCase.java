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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.transformer.asm.ror.IFEQ_2_IFNE_Transition;
import com.retroduction.carma.utilities.ByteCodeFileReader;

public class JCovCodecHexTestCase extends TestCase {

	private final static String SAMPLE_CLASS_FILENAME = "src/test/it/jcov_codec_hex/Hex.class";

	public void testGetLineInfo() throws FileNotFoundException, IOException {

		byte[] byteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		IFEQ_2_IFNE_Transition asmTransformer = new IFEQ_2_IFNE_Transition();
		List<Mutant> mutants = asmTransformer.applyTransitions(byteCode);

		assertEquals(3, mutants.size());

		assertEquals(58, mutants.get(0).getSourceMapping().getLineStart());
		assertEquals(58, mutants.get(0).getSourceMapping().getLineEnd());
		assertEquals(13, mutants.get(0).getSourceMapping().getColumnStart());
		assertEquals(30, mutants.get(0).getSourceMapping().getColumnEnd());

		assertEquals(150, mutants.get(1).getSourceMapping().getLineStart());
		assertEquals(150, mutants.get(1).getSourceMapping().getLineEnd());
		assertEquals(32, mutants.get(1).getSourceMapping().getColumnStart());
		assertEquals(56, mutants.get(1).getSourceMapping().getColumnEnd());

		assertEquals(184, mutants.get(2).getSourceMapping().getLineStart());
		assertEquals(184, mutants.get(2).getSourceMapping().getLineEnd());
		assertEquals(32, mutants.get(2).getSourceMapping().getColumnStart());
		assertEquals(56, mutants.get(2).getSourceMapping().getColumnEnd());

	}

}
