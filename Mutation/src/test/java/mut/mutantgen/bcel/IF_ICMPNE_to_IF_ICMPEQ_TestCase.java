package mut.mutantgen.bcel;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;

public class IF_ICMPNE_to_IF_ICMPEQ_TestCase extends TestCase {

	// FileWriter writer = new FileWriter("mod.class");
	// writer.write(new String(byteCode[0]));
	// writer.flush();
	// writer.close();

	private class TestClassLoader extends ClassLoader {

		private void override(String binaryName, byte[] byteCode) {
			super.defineClass(binaryName, byteCode, 0, byteCode.length);
		}
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_CheckNumberOfMutants()
			throws Exception {

		JavaClass clazz = Repository.lookupClass(InputClass.class);

		BCELMutantCreator bcel = new BCELMutantCreator();

		byte[][] byteCode = bcel.getModifiedByteCodeForClass(clazz,
				InputClass.class.getMethod("branch", new Class[] { int.class,
						int.class }), "IF_ICMPNE");

		assertEquals("Number of first level mutants incorrect", 2,
				byteCode.length);
	}

	public void test_OriginalClassConditionalCases() {
		assertEquals("Unmodified expectation not met", 3, new InputClass()
				.branch(5, 5));
		assertEquals("Unmodified expectation not met", 2, new InputClass()
				.branch(1, 5));
		assertEquals("Unmodified expectation not met", 1, new InputClass()
				.branch(1, 2));
		assertEquals("Unmodified expectation not met", 3, new InputClass()
				.branch(5, 2));
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_FirstBranchModifiedOnlyMutant_Case1()
			throws Exception {

		JavaClass clazz = Repository.lookupClass(InputClass.class);

		BCELMutantCreator bcel = new BCELMutantCreator();

		byte[][] byteCode = bcel.getModifiedByteCodeForClass(clazz,
				InputClass.class.getMethod("branch", new Class[] { int.class,
						int.class }), "IF_ICMPNE");

		TestClassLoader loader = new TestClassLoader();

		loader.override("mut.mutantgen.bcel.InputClass", byteCode[0]);

		Class modifiedInputClass = loader
				.loadClass("mut.mutantgen.bcel.InputClass");

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch",
				new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance,
				new Object[] { 5, 5 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(2, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_FirstBranchModifiedOnlyMutant_Case2()
			throws Exception {

		JavaClass clazz = Repository.lookupClass(InputClass.class);

		BCELMutantCreator bcel = new BCELMutantCreator();

		byte[][] byteCode = bcel.getModifiedByteCodeForClass(clazz,
				InputClass.class.getMethod("branch", new Class[] { int.class,
						int.class }), "IF_ICMPNE");

		TestClassLoader loader = new TestClassLoader();

		loader.override("mut.mutantgen.bcel.InputClass", byteCode[0]);

		Class modifiedInputClass = loader
				.loadClass("mut.mutantgen.bcel.InputClass");

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch",
				new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance,
				new Object[] { 1, 5 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(3, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_FirstBranchModifiedOnlyMutant_Case3()
			throws Exception {

		JavaClass clazz = Repository.lookupClass(InputClass.class);

		BCELMutantCreator bcel = new BCELMutantCreator();

		byte[][] byteCode = bcel.getModifiedByteCodeForClass(clazz,
				InputClass.class.getMethod("branch", new Class[] { int.class,
						int.class }), "IF_ICMPNE");

		TestClassLoader loader = new TestClassLoader();

		loader.override("mut.mutantgen.bcel.InputClass", byteCode[0]);

		Class modifiedInputClass = loader
				.loadClass("mut.mutantgen.bcel.InputClass");

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch",
				new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance,
				new Object[] { 1, 2 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(3, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_FirstBranchModifiedOnlyMutant_Case4()
			throws Exception {

		JavaClass clazz = Repository.lookupClass(InputClass.class);

		BCELMutantCreator bcel = new BCELMutantCreator();

		byte[][] byteCode = bcel.getModifiedByteCodeForClass(clazz,
				InputClass.class.getMethod("branch", new Class[] { int.class,
						int.class }), "IF_ICMPNE");

		TestClassLoader loader = new TestClassLoader();

		loader.override("mut.mutantgen.bcel.InputClass", byteCode[0]);

		Class modifiedInputClass = loader
				.loadClass("mut.mutantgen.bcel.InputClass");

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch",
				new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance,
				new Object[] { 5, 2 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(1, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_SecondBranchModifiedOnlyMutant_Case1()
			throws Exception {

		JavaClass clazz = Repository.lookupClass(InputClass.class);

		BCELMutantCreator bcel = new BCELMutantCreator();

		byte[][] byteCode = bcel.getModifiedByteCodeForClass(clazz,
				InputClass.class.getMethod("branch", new Class[] { int.class,
						int.class }), "IF_ICMPNE");

		TestClassLoader loader = new TestClassLoader();

		loader.override("mut.mutantgen.bcel.InputClass", byteCode[1]);

		Class modifiedInputClass = loader
				.loadClass("mut.mutantgen.bcel.InputClass");

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch",
				new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance,
				new Object[] { 5, 5 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(3, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_SecondBranchModifiedOnlyMutant_Case2()
			throws Exception {

		JavaClass clazz = Repository.lookupClass(InputClass.class);

		BCELMutantCreator bcel = new BCELMutantCreator();

		byte[][] byteCode = bcel.getModifiedByteCodeForClass(clazz,
				InputClass.class.getMethod("branch", new Class[] { int.class,
						int.class }), "IF_ICMPNE");

		TestClassLoader loader = new TestClassLoader();

		loader.override("mut.mutantgen.bcel.InputClass", byteCode[1]);

		Class modifiedInputClass = loader
				.loadClass("mut.mutantgen.bcel.InputClass");

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch",
				new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance,
				new Object[] { 1, 5 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(1, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_SecondBranchModifiedOnlyMutant_Case3()
			throws Exception {

		JavaClass clazz = Repository.lookupClass(InputClass.class);

		BCELMutantCreator bcel = new BCELMutantCreator();

		byte[][] byteCode = bcel.getModifiedByteCodeForClass(clazz,
				InputClass.class.getMethod("branch", new Class[] { int.class,
						int.class }), "IF_ICMPNE");

		TestClassLoader loader = new TestClassLoader();

		loader.override("mut.mutantgen.bcel.InputClass", byteCode[1]);

		Class modifiedInputClass = loader
				.loadClass("mut.mutantgen.bcel.InputClass");

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch",
				new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance,
				new Object[] { 1, 2 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(2, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_SecondBranchModifiedOnlyMutant_Case4()
			throws Exception {

		JavaClass clazz = Repository.lookupClass(InputClass.class);

		BCELMutantCreator bcel = new BCELMutantCreator();

		byte[][] byteCode = bcel.getModifiedByteCodeForClass(clazz,
				InputClass.class.getMethod("branch", new Class[] { int.class,
						int.class }), "IF_ICMPNE");

		TestClassLoader loader = new TestClassLoader();

		loader.override("mut.mutantgen.bcel.InputClass", byteCode[1]);

		Class modifiedInputClass = loader
				.loadClass("mut.mutantgen.bcel.InputClass");

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch",
				new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance,
				new Object[] { 5, 2 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(3, resultObject);
	}

}
