package com.mutation.runner;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import junit.framework.TestCase;

import com.mutation.runner.events.EventListenerMock;
import com.mutation.runner.events.IEventListener;
import com.mutation.runner.utililties.ByteCodeFileReader;
import com.mutation.transform.asm.ror.IF_ICMPEQ_2_IF_ICMPNE_Transition;
import com.mutation.transform.asm.ror.IF_ICMPNE_2_IF_ICMPEQ_Transition;
import com.mutation.transform.asm.ror.ROR_Transition;

public class ROR_Transition_TestCase extends TestCase {

	private class TestClassLoader extends ClassLoader {

		private void override(String binaryName, byte[] byteCode) {
			super.defineClass(binaryName, byteCode, 0, byteCode.length);
		}
	}

	public void test_ApplyTransition_NonConcurrent() throws Exception {

		ROR_Transition ror = new IF_ICMPEQ_2_IF_ICMPNE_Transition();

		IEventListener listener = new EventListenerMock();

		ByteCodeFileReader reader = new ByteCodeFileReader();

		byte[] testByteCode = reader.readByteCodeFromDisk(new File(
				"target/test-classes/com/mutation/runner/ROR_SampleClass.class"));

		List<Mutant> mutants = ror.applyTransitions(testByteCode, listener);

		assertEquals(2, mutants.size());

		{
			assertEquals(7, mutants.get(0).getSourceMapping().getLineNo());

			TestClassLoader loader = new TestClassLoader();

			loader.override("com.mutation.runner.ROR_SampleClass", mutants.get(0).getByteCode());

			Class modifiedInputClass = loader.loadClass("com.mutation.runner.ROR_SampleClass");

			Object modifiedInputClassInstance = modifiedInputClass.newInstance();

			Method branchMethod = modifiedInputClass.getMethod("IF_ICMPEQ", new Class[] { int.class });

			Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(4, resultObject);
		}

		{
			assertEquals(8, mutants.get(1).getSourceMapping().getLineNo());
			TestClassLoader loader = new TestClassLoader();
			loader.override("com.mutation.runner.ROR_SampleClass", mutants.get(1).getByteCode());

			Class modifiedInputClass = loader.loadClass("com.mutation.runner.ROR_SampleClass");

			Object modifiedInputClassInstance = modifiedInputClass.newInstance();

			Method branchMethod = modifiedInputClass.getMethod("IF_ICMPEQ", new Class[] { int.class });

			Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(3, resultObject);
		}

		{
			assertEquals(7, mutants.get(0).getSourceMapping().getLineNo());
			TestClassLoader loader = new TestClassLoader();
			loader.override("com.mutation.runner.ROR_SampleClass", mutants.get(0).getByteCode());

			Class modifiedInputClass = loader.loadClass("com.mutation.runner.ROR_SampleClass");

			Object modifiedInputClassInstance = modifiedInputClass.newInstance();

			Method branchMethod = modifiedInputClass.getMethod("IF_ICMPEQ", new Class[] { int.class });

			Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 2 });

			assertEquals(3, resultObject);
		}

		{
			assertEquals(8, mutants.get(1).getSourceMapping().getLineNo());
			TestClassLoader loader = new TestClassLoader();
			loader.override("com.mutation.runner.ROR_SampleClass", mutants.get(1).getByteCode());

			Class modifiedInputClass = loader.loadClass("com.mutation.runner.ROR_SampleClass");

			Object modifiedInputClassInstance = modifiedInputClass.newInstance();

			Method branchMethod = modifiedInputClass.getMethod("IF_ICMPEQ", new Class[] { int.class });

			Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 2 });

			assertEquals(4, resultObject);
		}
	}

	public void test_ApplyTransition_NonConcurrent_OtherTransition() throws Exception {

		ROR_Transition ror = new IF_ICMPNE_2_IF_ICMPEQ_Transition();

		IEventListener listener = new EventListenerMock();

		ByteCodeFileReader reader = new ByteCodeFileReader();

		byte[] testByteCode = reader.readByteCodeFromDisk(new File(
				"target/test-classes/com/mutation/runner/ROR_SampleClass.class"));

		List<Mutant> mutants = ror.applyTransitions(testByteCode, listener);

		assertEquals(1, mutants.size());

		{
			assertEquals(9, mutants.get(0).getSourceMapping().getLineNo());

			TestClassLoader loader = new TestClassLoader();

			loader.override("com.mutation.runner.ROR_SampleClass", mutants.get(0).getByteCode());

			Class modifiedInputClass = loader.loadClass("com.mutation.runner.ROR_SampleClass");

			Object modifiedInputClassInstance = modifiedInputClass.newInstance();

			Method branchMethod = modifiedInputClass.getMethod("IF_ICMPEQ", new Class[] { int.class });

			Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 1 });

			assertEquals(1, resultObject);
		}

		{
			assertEquals(9, mutants.get(0).getSourceMapping().getLineNo());
			TestClassLoader loader = new TestClassLoader();
			loader.override("com.mutation.runner.ROR_SampleClass", mutants.get(0).getByteCode());

			Class modifiedInputClass = loader.loadClass("com.mutation.runner.ROR_SampleClass");

			Object modifiedInputClassInstance = modifiedInputClass.newInstance();

			Method branchMethod = modifiedInputClass.getMethod("IF_ICMPEQ", new Class[] { int.class });

			Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 2 });

			assertEquals(4, resultObject);
		}

	}

}
