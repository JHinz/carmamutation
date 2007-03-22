package mut.mutantgen.bcel;

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

public class BCELMutantCreator {
	
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

}
