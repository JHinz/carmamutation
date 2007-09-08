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

	public void test_ApplyTransition_ByteCodeModsAreDistinctBetweenMutants() throws Exception {

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/aor/AOR_SampleClass.class";
		final String MUTANT1_BYTECODE_REFERENCE = "src/test/resources/com/retroduction/carma/transformer/aor/IADD_2_ISUB/mutant1ByteCode_ref.txt";
		final String MUTANT2_BYTECODE_REFERENCE = "src/test/resources/com/retroduction/carma/transformer/aor/IADD_2_ISUB/mutant2ByteCode_ref.txt";

		byte[] testByteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IADD_2_ISUB_Transition().applyTransitions(testByteCode);

		assertEquals(2, mutants.size());

		ASMTreeTranslator translator = new ASMTreeTranslator();

		String humanReadableMutant1ByteCode = translator.getHumanReadableByteCode(mutants.get(0).getByteCode());
		String humanReadableMutant2ByteCode = translator.getHumanReadableByteCode(mutants.get(1).getByteCode());

		String mutant1Reference = JUnitTraceAdapter.readReferenceText(new File(MUTANT1_BYTECODE_REFERENCE));
		String mutant2Reference = JUnitTraceAdapter.readReferenceText(new File(MUTANT2_BYTECODE_REFERENCE));

		assertEquals(humanReadableMutant1ByteCode, mutant1Reference);
		assertEquals(humanReadableMutant2ByteCode, mutant2Reference);

	}

	public void test_ApplyTransition_IADD_2_ISUB() throws Exception {

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/aor/AOR_SampleClass.class";
		final String MUTANT1_BYTECODE_REFERENCE = "src/test/resources/com/retroduction/carma/transformer/aor/IADD_2_ISUB/mutant1ByteCode_ref.txt";

		byte[] testByteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IADD_2_ISUB_Transition().applyTransitions(testByteCode);

		assertEquals(2, mutants.size());

		ASMTreeTranslator translator = new ASMTreeTranslator();

		String humanReadableMutant1ByteCode = translator.getHumanReadableByteCode(mutants.get(0).getByteCode());

		String mutant1Reference = JUnitTraceAdapter.readReferenceText(new File(MUTANT1_BYTECODE_REFERENCE));

		assertEquals(humanReadableMutant1ByteCode, mutant1Reference);

	}
	
//	public void test_ApplyTransition_ISUB_2_IADD() throws Exception {
//
//		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/aor/AOR_SampleClass.class";
//		final String MUTANT_BYTECODE_REFERENCE = "src/test/resources/com/retroduction/carma/transformer/aor/IADD_2_ISUB/mutant_ISUB_2_IADD_ref.txt";
//
//		byte[] testByteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));
//
//		List<Mutant> mutants = new IADD_2_ISUB_Transition().applyTransitions(testByteCode);
//
//		assertEquals(2, mutants.size());
//
//		ASMTreeTranslator translator = new ASMTreeTranslator();
//
//		String humanReadableMutant1ByteCode = translator.getHumanReadableByteCode(mutants.get(0).getByteCode());
//
//		String mutant1Reference = JUnitTraceAdapter.readReferenceText(new File(MUTANT_BYTECODE_REFERENCE));
//
//		assertEquals(humanReadableMutant1ByteCode, mutant1Reference);
//
//	}

	public void test_IADD_2_ISUB_SmokeTest() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.aor.AOR_SampleClass";
		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/aor/AOR_SampleClass.class";
		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "add3";

		byte[] testByteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IADD_2_ISUB_Transition().applyTransitions(testByteCode);

		assertEquals(2, mutants.size());

		{
			assertEquals(17, mutants.get(0).getSourceMapping().getLineNo());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass.newInstance();

			Method branchMethod = modifiedInputClass
					.getMethod(SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(-3, resultObject0);

			Object resultObject1 = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(-2, resultObject1);
		}

	}

}
