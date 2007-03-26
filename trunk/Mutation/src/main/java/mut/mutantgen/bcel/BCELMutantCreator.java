package mut.mutantgen.bcel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mut.EMutationOperator;
import mut.EMutationType;
import mut.IMutantGenerator;
import mut.Mutant;
import mut.SourceCodeMapping;
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

public class BCELMutantCreator implements IMutantGenerator {

	public List<Mutant> generateMutants(String classUnderTest, Set<EMutationType> operators) {

		List<Mutant> result = new ArrayList<Mutant>();

		try {

			JavaClass clazz = Repository.lookupClass(classUnderTest);

			Method[] methods = clazz.getMethods();
			for (int methodCounter = 0; methodCounter < methods.length; methodCounter++) {
				List<Mutant> mutants = generateMutants(clazz, methods[methodCounter], operators);
				result.addAll(mutants);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;

	}

	private MutationRepository mutationRepository = new MutationRepository();

	private List<Mutant> generateMutants(JavaClass clazz, Method bcelMethod, Set<EMutationType> mutationTypes)
			throws ClassNotFoundException {

		List<Mutant> mutants = new ArrayList<Mutant>();

		for (EMutationType mutationType : mutationTypes) {

			List<EMutationOperator> operators = mutationRepository.getOperatorMapping(mutationType);

			if (operators == null || operators.size() == 0)
				return mutants;

			Code code = bcelMethod.getCode();

			if (code == null)
				return mutants;

			InstructionList instructions = new InstructionList(bcelMethod.getCode().getCode());

			InstructionFinder finder = new InstructionFinder(instructions);

			for (EMutationOperator operator : operators) {
				Iterator instructionIterator = finder.search(operator.name());

				while (instructionIterator.hasNext()) {

					InstructionHandle[] handles = (InstructionHandle[]) instructionIterator.next();

					IMutator mutator = mutationRepository.getMutator(operator);

					Instruction originalInstruction = handles[0].getInstruction().copy();

					mutator.performMutation(handles[0]);

					ClassGen classGen = new ClassGen(clazz);

					MethodGen methodGen = new MethodGen(bcelMethod, clazz.getClassName(), classGen.getConstantPool());

					methodGen.setInstructionList(instructions);

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
					newMutant.setMutationType(mutationType);
					newMutant.setMutationOperator(mutator.getMutationOperator());

					mutants.add(newMutant);

					handles[0].setInstruction(originalInstruction);

				}
			}
			instructions.dispose();
		}

		return mutants;
	}

}