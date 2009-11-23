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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.transformer.asm.ror.IFEQ_2_IFNE_Transition;
import com.retroduction.carma.transformer.asm.ror.IFNE_2_IFEQ_Transition;
import com.retroduction.carma.utilities.ByteCodeFileReader;

public class JCovCodecTestCase extends TestCase {

	private final static String SAMPLE_CLASS_FILENAME = "src/test/it/jcov_codec/RFC1522Codec.class";

	public void testGetLineInfo() throws FileNotFoundException, IOException {

		byte[] byteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		HashMap<Integer, List<Mutant>> mutantsPerLine = new HashMap<Integer, List<Mutant>>();

		{
			IFEQ_2_IFNE_Transition asmTransformer = new IFEQ_2_IFNE_Transition();
			List<Mutant> mutants = asmTransformer.applyTransitions(byteCode);

			for (Mutant mutant : mutants) {

				if (!mutantsPerLine.containsKey(mutant.getSourceMapping().getLineStart()))
					mutantsPerLine.put(mutant.getSourceMapping().getLineStart(),
							new ArrayList<Mutant>());

				List<Mutant> mutantsAtLine = mutantsPerLine.get(mutant.getSourceMapping()
						.getLineStart());

				mutantsAtLine.add(mutant);
			}
		}

		{
			IFNE_2_IFEQ_Transition asmTransformer = new IFNE_2_IFEQ_Transition();
			List<Mutant> mutants = asmTransformer.applyTransitions(byteCode);

			for (Mutant mutant : mutants) {

				if (!mutantsPerLine.containsKey(mutant.getSourceMapping().getLineStart()))
					mutantsPerLine.put(mutant.getSourceMapping().getLineStart(),
							new ArrayList<Mutant>());

				List<Mutant> mutantsAtLine = mutantsPerLine.get(mutant.getSourceMapping()
						.getLineStart());

				mutantsAtLine.add(mutant);
			}
		}

		Mutant mutant = null;

		{

			assertEquals(2, mutantsPerLine.get(101).size());

			mutant = mutantsPerLine.get(101).get(0);

			assertEquals(101, mutant.getSourceMapping().getLineStart());
			assertEquals(101, mutant.getSourceMapping().getLineEnd());
			assertEquals(13, mutant.getSourceMapping().getColumnStart());
			assertEquals(37, mutant.getSourceMapping().getColumnEnd());

			mutant = mutantsPerLine.get(101).get(1);

			assertEquals(101, mutant.getSourceMapping().getLineStart());
			assertEquals(101, mutant.getSourceMapping().getLineEnd());
			assertEquals(13, mutant.getSourceMapping().getColumnStart());
			assertEquals(63, mutant.getSourceMapping().getColumnEnd());
		}

		{

			assertEquals(1, mutantsPerLine.get(111).size());

			mutant = mutantsPerLine.get(111).get(0);

			assertEquals(111, mutant.getSourceMapping().getLineStart());
			assertEquals(111, mutant.getSourceMapping().getLineEnd());
			assertEquals(13, mutant.getSourceMapping().getColumnStart());
			assertEquals(31, mutant.getSourceMapping().getColumnEnd());
		}
	}

}
