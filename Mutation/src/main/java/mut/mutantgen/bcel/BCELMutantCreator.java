package mut.mutantgen.bcel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.IF_ICMPEQ;
import org.apache.bcel.generic.IF_ICMPNE;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.util.InstructionFinder;

public class BCELMutantCreator {

	public List<byte[]> getModifiedByteCodesForClass(String classToBeModified,
			String methodToBeModified) throws ClassNotFoundException {

		ArrayList<byte[]> byteCodeResults = new ArrayList<byte[]>();

		return byteCodeResults;

	}

	public byte[] getModifiedByteCodeForClass(String classToBeModified,
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

		// InstructionFinder finder = new InstructionFinder(instructions);
		//
		// Iterator instructionIterator = finder.search("BIPUSH");
		//
		// while (instructionIterator.hasNext()) {
		// InstructionHandle[] handles = (InstructionHandle[])
		// instructionIterator
		// .next();
		// for (int i = 0; i < handles.length; i++) {
		//
		// Instruction instruction = handles[i].getInstruction();
		// System.out.println("Instruction: " + instruction);
		// handles[i].swapInstruction(new BIPUSH(new Integer(41)
		// .byteValue()));
		//
		// }
		//
		// }
		//
		// instructions.append(new IRETURN());
		//		

		MethodGen methodGen = new MethodGen(methodGet42, clazz.getClassName(),
				classGen.getConstantPool());
		methodGen.setMaxStack();
		methodGen.setInstructionList(instructions);

		classGen.replaceMethod(methodGet42, methodGen.getMethod());

		instructions.dispose();

		return classGen.getJavaClass().getBytes();
	}

	public byte[][] getModifiedByteCodeForClass(JavaClass clazz,
			java.lang.reflect.Method method, String byteCodeInstruction)
			throws ClassNotFoundException {

		Method bcelMethod = clazz.getMethod(method);

		InstructionList instructions = new InstructionList(bcelMethod.getCode()
				.getCode());

		InstructionFinder finder = new InstructionFinder(instructions);

		Iterator instructionIterator = finder.search(byteCodeInstruction);

		Vector<byte[]> byteCodeModifiedClasses = new Vector<byte[]>();

		while (instructionIterator.hasNext()) {

			InstructionHandle[] handles = (InstructionHandle[]) instructionIterator
					.next();

			handles[0].setInstruction(((IF_ICMPNE) handles[0].getInstruction())
					.negate());

			// instructions.setPositions();

			ClassGen classGen = new ClassGen(clazz);

			MethodGen methodGen = new MethodGen(bcelMethod, clazz
					.getClassName(), classGen.getConstantPool());

			methodGen.setInstructionList(instructions);

			classGen.replaceMethod(bcelMethod, methodGen.getMethod());

			// methodGen.setMaxLocals();
			// methodGen.setMaxStack();

			byteCodeModifiedClasses.add(classGen.getJavaClass().getBytes());

			handles[0].setInstruction(((IF_ICMPEQ) handles[0].getInstruction())
					.negate());

			// instructions.setPositions();

		}

		instructions.dispose();

		return byteCodeModifiedClasses.toArray(new byte[0][]);
	}
}
