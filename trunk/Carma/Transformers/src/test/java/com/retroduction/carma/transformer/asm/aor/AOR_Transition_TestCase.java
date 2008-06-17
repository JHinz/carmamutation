/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.transformer.asm.aor;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.util.ASMTreeTranslator;
import com.retroduction.carma.util.JUnitTraceAdapter;
import com.retroduction.carma.utilities.ByteCodeFileReader;

public class AOR_Transition_TestCase extends TestCase {

	private class TestClassLoader extends ClassLoader {

		private void override(String binaryName, byte[] byteCode) {
			super.defineClass(binaryName, byteCode, 0, byteCode.length);
		}
	}

	public void test_ApplyTransition_IADD_2_ISUB() throws Exception {

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/aor/AOR_SampleClass.class";
		final String MUTANT1_BYTECODE_REFERENCE = "src/test/resources/com/retroduction/carma/transformer/aor/mutant_IADD_2_ISUB_ref.txt";

		byte[] testByteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IADD_2_ISUB_Transition().applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		ASMTreeTranslator translator = new ASMTreeTranslator();

		String humanReadableMutant1ByteCode = translator.getHumanReadableByteCode(mutants.get(0).getByteCode());

		String mutant1Reference = JUnitTraceAdapter.readReferenceText(new File(MUTANT1_BYTECODE_REFERENCE));

		// TODO: Bad idea - breaks with compiler debug false option
		// assertEquals(humanReadableMutant1ByteCode, mutant1Reference);

	}
	
	public void test_ApplyTransition_ISUB_2_IADD() throws Exception {

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/aor/AOR_SampleClass.class";
		final String MUTANT_BYTECODE_REFERENCE = "src/test/resources/com/retroduction/carma/transformer/aor/mutant_ISUB_2_IADD_ref.txt";

		byte[] testByteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new ISUB_2_IADD_Transition().applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		ASMTreeTranslator translator = new ASMTreeTranslator();

		String humanReadableMutant1ByteCode = translator.getHumanReadableByteCode(mutants.get(0).getByteCode());

		String mutant1Reference = JUnitTraceAdapter.readReferenceText(new File(MUTANT_BYTECODE_REFERENCE));

		// TODO: Bad idea - breaks with compiler debug false option
		// assertEquals(humanReadableMutant1ByteCode, mutant1Reference);

	}
	
	public void test_ApplyTransition_IMUL_2_IDIV() throws Exception {

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/aor/AOR_SampleClass.class";
		final String MUTANT_BYTECODE_REFERENCE = "src/test/resources/com/retroduction/carma/transformer/aor/mutant_IMUL_2_IDIV_ref.txt";

		byte[] testByteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IMUL_2_IDIV_Transition().applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		ASMTreeTranslator translator = new ASMTreeTranslator();

		String humanReadableMutant1ByteCode = translator.getHumanReadableByteCode(mutants.get(0).getByteCode());

		String mutantReference = JUnitTraceAdapter.readReferenceText(new File(MUTANT_BYTECODE_REFERENCE));

		// TODO: Bad idea - breaks with compiler debug false option
		// assertEquals(humanReadableMutant1ByteCode, mutantReference);

	}
	
	public void test_ApplyTransition_IDIV_2_IMUL() throws Exception {

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/aor/AOR_SampleClass.class";
		final String MUTANT_BYTECODE_REFERENCE = "src/test/resources/com/retroduction/carma/transformer/aor/mutant_IDIV_2_IMUL_ref.txt";

		byte[] testByteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IDIV_2_IMUL_Transition().applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		ASMTreeTranslator translator = new ASMTreeTranslator();

		String humanReadableMutant1ByteCode = translator.getHumanReadableByteCode(mutants.get(0).getByteCode());

		String mutantReference = JUnitTraceAdapter.readReferenceText(new File(MUTANT_BYTECODE_REFERENCE));

		// TODO: Bad idea - breaks with compiler debug false option
		// assertEquals(humanReadableMutant1ByteCode, mutantReference);

	}

	public void test_IADD_2_ISUB_SmokeTest() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.aor.AOR_SampleClass";
		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/aor/AOR_SampleClass.class";
		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "calculate";

		byte[] testByteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IADD_2_ISUB_Transition().applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(17, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass.newInstance();

			Method branchMethod = modifiedInputClass
					.getMethod(SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			branchMethod.invoke(modifiedInputClassInstance, new Object[] { 0 });
		}

	}
	
	public void test_ApplyTransition_IADD_2_ISUB_MultipleHits() throws Exception {

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/aor/AOR_MultipleHitsSampleClass.class";
		final String MUTANT1_BYTECODE_REFERENCE = "src/test/resources/com/retroduction/carma/transformer/aor/mutant_multiple_ocurrences_hit1_ref.txt";
		final String MUTANT2_BYTECODE_REFERENCE = "src/test/resources/com/retroduction/carma/transformer/aor/mutant_multiple_ocurrences_hit2_ref.txt";

		byte[] testByteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IADD_2_ISUB_Transition().applyTransitions(testByteCode);

		assertEquals(2, mutants.size());

		ASMTreeTranslator translator = new ASMTreeTranslator();

		String humanReadableMutant1ByteCode = translator.getHumanReadableByteCode(mutants.get(0).getByteCode());
		String humanReadableMutant2ByteCode = translator.getHumanReadableByteCode(mutants.get(1).getByteCode());

		String mutant1Reference = JUnitTraceAdapter.readReferenceText(new File(MUTANT1_BYTECODE_REFERENCE));
		String mutant2Reference = JUnitTraceAdapter.readReferenceText(new File(MUTANT2_BYTECODE_REFERENCE));

		// TODO: Bad idea - breaks with compiler debug false option
		// assertEquals(humanReadableMutant1ByteCode, mutant1Reference);
		// TODO: Bad idea - breaks with compiler debug false option
		// assertEquals(humanReadableMutant2ByteCode, mutant2Reference);

	}

}
