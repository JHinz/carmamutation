package com.mutation;

import java.util.HashMap;
import java.util.Iterator;

import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.textui.TestRunner;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.BIPUSH;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.IRETURN;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.util.InstructionFinder;

import com.mutation.test.ClassUnderTest;

public class Mutator extends ClassLoader {

	private HashMap<String, Class> loadedClasses = new HashMap<String, Class>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new Mutator();

	}

	public Mutator(ClassLoader parent) {
		super(parent);

	}

	public Class overrideClass(String name, byte[] b, int off, int len) {
		Class clazz = defineClass(name, b, 0, b.length);
		this.loadedClasses.put(name, clazz);
		return clazz;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		if (loadedClasses.containsKey(name)) {
			return loadedClasses.get(name);
		}
		return super.findClass(name);
	}

	@Override
	public Class<?> loadClass(String arg0) throws ClassNotFoundException {
		if (loadedClasses.containsKey(arg0)) {
			return loadedClasses.get(arg0);
		}
		return super.loadClass(arg0);
	}

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		if (loadedClasses.containsKey(name)) {
			return loadedClasses.get(name);
		}
		return super.loadClass(name, resolve);
	}

	private byte[] getModifiedByteCodeForClass(String classToBeModified,
			String methodToBeModified) throws ClassNotFoundException {

		JavaClass clazz = Repository.lookupClass(Class
				.forName(classToBeModified));

		ClassGen classGen = new ClassGen(clazz);

		Method methodGet42 = null;

		for (Method method : clazz.getMethods()) {
			System.out.println("Method: " + method.getName());
			if (method.getName().equals(methodToBeModified)) {
				methodGet42 = method;
			}
		}

		InstructionList instructions = new InstructionList(methodGet42
				.getCode().getCode());

		InstructionFinder finder = new InstructionFinder(instructions);

		Iterator instructionIterator = finder.search("BIPUSH");

		while (instructionIterator.hasNext()) {
			InstructionHandle[] handles = (InstructionHandle[]) instructionIterator
					.next();
			for (int i = 0; i < handles.length; i++) {

				Instruction instruction = handles[i].getInstruction();
				System.out.println("Instruction: " + instruction);
				handles[i].swapInstruction(new BIPUSH(new Integer(41)
						.byteValue()));

			}

		}

		instructions.append(new IRETURN());

		MethodGen methodGen = new MethodGen(methodGet42, clazz.getClassName(),
				classGen.getConstantPool());
		methodGen.setInstructionList(instructions);

		classGen.replaceMethod(methodGet42, methodGen.getMethod());

		instructions.dispose();

		return classGen.getJavaClass().getBytes();
	}

	public Mutator() {

		try {

			String classToBeModified = "com.mutation.test.ClassUnderTest";

			String methodToBeModified = "get42";

			byte[] modifiedByteCode = getModifiedByteCodeForClass(
					classToBeModified, methodToBeModified);

			overrideClass(classToBeModified, modifiedByteCode, 0,
					modifiedByteCode.length);

			printResultWithStandardConstructor();

			printResultWithWrapperInstance();

			printResultWithReflection();

			runJUnit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printResultWithReflection() {
		try {

			Class clazzUnderTest = loadClass("com.mutation.test.ClassUnderTest");

			Object o = clazzUnderTest.newInstance();

			java.lang.reflect.Method method = clazzUnderTest.getMethod("get42",
					new Class[] {});

			Object result = method.invoke(o, new Object[] {});

			System.out.println("Result via reflection: " + result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void runJUnit() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		Class testCaseClazz = loadClass("com.mutation.test.TestClass");

		TestCase testCase = (TestCase) testCaseClazz.newInstance();

		testCase.setName("testGet42");
		TestResult testResult = TestRunner.run(testCase);
	}

	private void printResultWithStandardConstructor() {
		ClassUnderTest bla = new ClassUnderTest();
		System.out.println("Result locally: " + bla.get42());
	}

	private void printResultWithWrapperInstance() {
		new WrapperInstance();
	}
}
