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
package com.retroduction.carma.transformer.asm.ror;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.utilities.ByteCodeFileReader;

public class ROR_Transition_TestCase extends TestCase {

	private class TestClassLoader extends ClassLoader {

		private void override(String binaryName, byte[] byteCode) {
			super.defineClass(binaryName, byteCode, 0, byteCode.length);
		}
	}

	public void test_ApplyTransition_ByteCodeModsAreDistinctBetweenMutants()
			throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.Mixed_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/Mixed_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "useMultiple_IF_ICMPEQ_And_IF_ICMPNE";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IF_ICMPNE_2_IF_ICMPEQ_Transition()
				.applyTransitions(testByteCode);

		assertEquals(2, mutants.size());

		{
			assertEquals(17, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(4, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 2 });

			assertEquals(3, resultObject1);
		}

		{
			assertEquals(18, mutants.get(1).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(1).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(3, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 2 });

			assertEquals(4, resultObject1);
		}
	}

	public void test_IF_ICMPEQ_2_IF_ICMPNE() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.EQ_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/EQ_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IF_ICMPEQ";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IF_ICMPEQ_2_IF_ICMPNE_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(17, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(1, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(0, resultObject1);
		}

	}

	public void test_IF_ICMPNE_2_IF_ICMPEQ() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.NE_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/NE_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IF_ICMPNE";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IF_ICMPNE_2_IF_ICMPEQ_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(17, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(0, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(1, resultObject1);
		}

	}

	public void test_IF_ICMPGE_2_IF_ICMPLT() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.GE_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/GE_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IF_ICMPGE";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IF_ICMPGE_2_IF_ICMPLT_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(17, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(1, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(0, resultObject1);

			Object resultObject2 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 2 });

			assertEquals(0, resultObject2);
		}

	}

	public void test_IF_ICMPLT_2_IF_ICMPGT() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.LT_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/LT_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IF_ICMPLT";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IF_ICMPLT_2_IF_ICMPGE_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(17, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(0, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(1, resultObject1);

			Object resultObject2 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 2 });

			assertEquals(1, resultObject2);
		}

	}

	public void test_IF_ICMPGT_2_IF_ICMPLE() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.GT_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/GT_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IF_ICMPGT";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IF_ICMPGT_2_IF_ICMPLE_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(17, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(1, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(1, resultObject1);

			Object resultObject2 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 2 });

			assertEquals(0, resultObject2);
		}

	}

	public void test_IF_ICMPLE_2_IF_ICMPGT() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.LE_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/LE_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IF_ICMPLE";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IF_ICMPLE_2_IF_ICMPGT_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(17, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(0, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(0, resultObject1);

			Object resultObject2 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 2 });

			assertEquals(1, resultObject2);
		}

	}

	public void test_IFEQ_2_IFNE() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.EQ_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/EQ_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IFEQ";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IFEQ_2_IFNE_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(27, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(0, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(1, resultObject1);
		}

	}

	public void test_IFNE_2_IFEQ() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.NE_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/NE_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IFNE";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IFNE_2_IFEQ_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(27, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(1, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(0, resultObject1);
		}

	}

	public void test_IFGE_2_IFLT() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.GE_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/GE_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IFGE";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IFGE_2_IFLT_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(27, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { -1 });

			assertEquals(1, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(0, resultObject1);

			Object resultObject2 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(0, resultObject2);
		}

	}

	public void test_IFLT_2_IFGT() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.LT_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/LT_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IFLT";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IFLT_2_IFGE_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(27, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { -1 });

			assertEquals(0, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(1, resultObject1);

			Object resultObject2 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(1, resultObject2);
		}

	}

	public void test_IFGT_2_IFLE() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.GT_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/GT_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IFGT";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IFGT_2_IFLE_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(27, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { -1 });

			assertEquals(1, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(1, resultObject1);

			Object resultObject2 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(0, resultObject2);
		}

	}

	public void test_IFLE_2_IFGT() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.LE_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/LE_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IFLE";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IFLE_2_IFGT_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(27, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { int.class });

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { -1 });

			assertEquals(0, resultObject0);

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 0 });

			assertEquals(0, resultObject1);

			Object resultObject2 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(1, resultObject2);
		}

	}

	public void test_IF_ACMPEQ_2_IF_ACMPNE() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.EQ_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/EQ_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IF_ACMPEQ";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IF_ACMPEQ_2_IF_ACMPNE_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(37, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { Object.class,
							Object.class });

			Object a1 = new Object();
			Object b1 = a1;

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { a1, b1 });

			assertEquals(0, resultObject0);

			b1 = new Object();

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { a1, b1 });

			assertEquals(1, resultObject1);
		}

	}

	public void test_IF_ACMPNE_2_IF_ACMPEQ() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.NE_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/NE_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IF_ACMPNE";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IF_ACMPNE_2_IF_ACMPEQ_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(37, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass.getMethod(
					SAMPLE_METHOD_ON_SAMPLE_CLASS, new Class[] { Object.class,
							Object.class });

			Object a1 = new Object();
			Object b1 = a1;

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { a1, b1 });

			assertEquals(1, resultObject0);

			b1 = new Object();

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { a1, b1 });

			assertEquals(0, resultObject1);
		}

	}

	public void test_IFNULL_2_IFNOTNULL() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.NULL_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/NULL_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IFNULL";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IFNULL_2_IFNONNULL_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(17, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass
					.getMethod(SAMPLE_METHOD_ON_SAMPLE_CLASS,
							new Class[] { Object.class });

			Object a1 = null;

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { a1 });

			assertEquals(0, resultObject0);

			a1 = new Object();

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { a1 });

			assertEquals(1, resultObject1);
		}

	}

	public void test_IFNONNULL_2_IFNULL() throws Exception {

		final String FQ_SAMPLE_CLASS_NAME = "com.retroduction.carma.transformer.asm.ror.NONNULL_SampleClass";

		final String SAMPLE_CLASS_FILENAME = "target/test-classes/com/retroduction/carma/transformer/asm/ror/NONNULL_SampleClass.class";

		final String SAMPLE_METHOD_ON_SAMPLE_CLASS = "methodWith_IFNONNULL";

		byte[] testByteCode = new ByteCodeFileReader()
				.readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		List<Mutant> mutants = new IFNONNULL_2_IFNULL_Transition()
				.applyTransitions(testByteCode);

		assertEquals(1, mutants.size());

		{
			assertEquals(17, mutants.get(0).getSourceMapping().getLineStart());

			TestClassLoader loader = new TestClassLoader();

			loader.override(FQ_SAMPLE_CLASS_NAME, mutants.get(0).getByteCode());

			Class<?> modifiedInputClass = loader.loadClass(FQ_SAMPLE_CLASS_NAME);

			Object modifiedInputClassInstance = modifiedInputClass
					.newInstance();

			Method branchMethod = modifiedInputClass
					.getMethod(SAMPLE_METHOD_ON_SAMPLE_CLASS,
							new Class[] { Object.class });

			Object a1 = null;

			Object resultObject0 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { a1 });

			assertEquals(1, resultObject0);

			a1 = new Object();

			Object resultObject1 = branchMethod.invoke(
					modifiedInputClassInstance, new Object[] { a1 });

			assertEquals(0, resultObject1);
		}

	}
}
