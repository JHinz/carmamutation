package com.retroduction.carma.transformer.asm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.transformer.asm.ror.IFEQ_2_IFNE_Transition;
import com.retroduction.carma.utilities.ByteCodeFileReader;

public class JCovCodecTestCase extends TestCase {

	private final static String SAMPLE_CLASS_FILENAME = "src/test/it/jcov_codec/RFC1522Codec.class";

	public void testGetLineInfo() throws FileNotFoundException, IOException {

		byte[] byteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		IFEQ_2_IFNE_Transition asmTransformer = new IFEQ_2_IFNE_Transition();
		List<Mutant> mutants = asmTransformer.applyTransitions(byteCode);

		// assertEquals(5, mutants.size());

		assertEquals(101, mutants.get(0).getSourceMapping().getLineStart());
		assertEquals(101, mutants.get(0).getSourceMapping().getLineEnd());
		assertEquals(13, mutants.get(0).getSourceMapping().getColumnStart());
		assertEquals(37, mutants.get(0).getSourceMapping().getColumnEnd());
		
		assertEquals(111, mutants.get(1).getSourceMapping().getLineStart());
		assertEquals(111, mutants.get(1).getSourceMapping().getLineEnd());
		assertEquals(13, mutants.get(1).getSourceMapping().getColumnStart());
		assertEquals(31, mutants.get(1).getSourceMapping().getColumnEnd());

	}

}
