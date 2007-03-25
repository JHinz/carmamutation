package mut.mutantgen.bcel;

import java.util.Iterator;
import java.util.Vector;

import mut.mutantgen.bcel.repository.MutationRepository;
import mut.mutantgen.bcel.repository.IMutator;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.IF_ICMPEQ;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.util.InstructionFinder;

public class BCELMutantCreator {

	private MutationRepository mutationRepository = new MutationRepository();

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

			IMutator mutator = mutationRepository
					.getMutator(MutationRepository.EMutation.IF_ICMPNE);

			mutator.performMutation_IF_ICMPNE(handles[0]);

			ClassGen classGen = new ClassGen(clazz);

			MethodGen methodGen = new MethodGen(bcelMethod, clazz
					.getClassName(), classGen.getConstantPool());

			methodGen.setInstructionList(instructions);

			classGen.replaceMethod(bcelMethod, methodGen.getMethod());

			byteCodeModifiedClasses.add(classGen.getJavaClass().getBytes());

			handles[0].setInstruction(((IF_ICMPEQ) handles[0].getInstruction())
					.negate());

		}

		instructions.dispose();

		return byteCodeModifiedClasses.toArray(new byte[0][]);
	}

}
