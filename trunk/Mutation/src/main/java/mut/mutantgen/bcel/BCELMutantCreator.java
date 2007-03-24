package mut.mutantgen.bcel;

import java.util.Iterator;
import java.util.Vector;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.IF_ICMPEQ;
import org.apache.bcel.generic.IF_ICMPNE;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.util.InstructionFinder;

public class BCELMutantCreator {

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
