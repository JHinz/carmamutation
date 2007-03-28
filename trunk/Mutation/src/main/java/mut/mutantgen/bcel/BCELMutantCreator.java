package mut.mutantgen.bcel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mut.mutantgen.bcel.repository.IMutator;
import mut.mutantgen.bcel.repository.MutationRepository;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.util.InstructionFinder;

import com.mutation.EMutationInstruction;
import com.mutation.EMutationOperator;
import com.mutation.IMutantGenerator;
import com.mutation.Mutant;
import com.mutation.SourceCodeMapping;
import com.mutation.events.IEventListener;

public class BCELMutantCreator implements IMutantGenerator {

	public List<Mutant> generateMutants(String classUnderTest, EMutationOperator operator, IEventListener listener) {

		List<Mutant> result = new ArrayList<Mutant>();

		try {

			JavaClass clazz = Repository.lookupClass(classUnderTest);

			Method[] methods = clazz.getMethods();
			for (int methodCounter = 0; methodCounter < methods.length; methodCounter++) {
				List<Mutant> mutants = generateMutants(clazz, methods[methodCounter], operator);
				result.addAll(mutants);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;

	}

	private MutationRepository mutationRepository = new MutationRepository();

	private List<Mutant> generateMutants(JavaClass clazz, Method bcelMethod, EMutationOperator operator)
			throws ClassNotFoundException {

		List<Mutant> mutants = new ArrayList<Mutant>();

		Set<EMutationInstruction> instructions = operator.getInstructions();

		if (instructions == null || instructions.size() == 0)
			return mutants;

		Code code = bcelMethod.getCode();

		if (code == null)
			return mutants;

		InstructionList bcelInstructions = new InstructionList(bcelMethod.getCode().getCode());

		InstructionFinder finder = new InstructionFinder(bcelInstructions);

		for (EMutationInstruction instruction : instructions) {
			Iterator instructionIterator = finder.search(instruction.name());

			while (instructionIterator.hasNext()) {

				InstructionHandle[] handles = (InstructionHandle[]) instructionIterator.next();

				IMutator mutator = mutationRepository.getMutator(instruction);

				Instruction originalInstruction = handles[0].getInstruction().copy();

				mutator.performMutation(handles[0]);

				ClassGen classGen = new ClassGen(clazz);

				MethodGen methodGen = new MethodGen(bcelMethod, clazz.getClassName(), classGen.getConstantPool());

				methodGen.setInstructionList(bcelInstructions);

				classGen.replaceMethod(bcelMethod, methodGen.getMethod());

				SourceCodeMapping sourceCodeMapping = new SourceCodeMapping();
				sourceCodeMapping.setClassName(clazz.getClassName());
				sourceCodeMapping.setSourceFile(clazz.getSourceFileName());
				sourceCodeMapping.setLineNo(bcelMethod.getCode().getLineNumberTable().getSourceLine(
						handles[0].getPosition()));

				Mutant newMutant = new Mutant();
				newMutant.setClassName(clazz.getClassName());
				newMutant.setSourceMapping(sourceCodeMapping);
				newMutant.setByteCode(classGen.getJavaClass().getBytes());
				newMutant.setMutationType(operator);
				newMutant.setMutationOperator(mutator.getMutationOperator());

				mutants.add(newMutant);

				handles[0].setInstruction(originalInstruction);

			}

			bcelInstructions.dispose();
		}

		return mutants;
	}

}
