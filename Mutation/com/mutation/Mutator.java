package com.mutation;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Iterator;

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

public class Mutator {

	private MutationClassLoader classLoader;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws MalformedURLException {
		new Mutator();
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
		super();

		this.classLoader = new MutationClassLoader();

		try {

			String classToBeModified = "com.mutation.test.ClassUnderTest";

			String methodToBeModified = "get42";

			byte[] modifiedByteCode = getModifiedByteCodeForClass(
					classToBeModified, methodToBeModified);

			this.classLoader.overrideClass(classToBeModified, modifiedByteCode,
					0, modifiedByteCode.length);

			printResultWithReflectionButWrapperInstance();

			printResultWithReflection();

			runJUnit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printResultWithReflection() {
		try {

			Class clazzUnderTest = this.classLoader
					.loadClass("com.mutation.test.ClassUnderTest");

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
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {

		Class testCaseClazz = this.classLoader
				.findClass("com.mutation.test.TestClass");

		System.out.println("testCaseClass classloader: "
				+ testCaseClazz.getClassLoader());

		java.lang.reflect.Method setNameMethod = testCaseClazz.getMethod(
				"setName", new Class[] { String.class });

		Object testCaseObject = testCaseClazz.newInstance();

		setNameMethod.invoke(testCaseObject, new Object[] { "testGet42" });

		Class jUnitTestClazz = this.classLoader
				.findClass("junit.framework.Test");

		System.out.println("jUnitTestCaseClazz classloader: "
				+ jUnitTestClazz.getClassLoader());

		Class jUnitTestRunnerClazz = this.classLoader
				.findClass("junit.textui.TestRunner");

		System.out.println("jUnitTestRunnerClazz classloader: "
				+ jUnitTestRunnerClazz.getClassLoader());

		java.lang.reflect.Method doRunMethod = jUnitTestRunnerClazz.getMethod(
				"run", new Class[] { jUnitTestClazz });

		doRunMethod.invoke(jUnitTestRunnerClazz,
				new Object[] { testCaseObject });

		// TestResult testResult = TestRunner.run(testCase);
	}

	private void printResultWithReflectionButWrapperInstance() {

		try {
			Class wrapperClass = this.classLoader
					.findClass("com.mutation.WrapperInstance");

			wrapperClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
