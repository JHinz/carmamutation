package mut.mutantgen.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import mut.Mutant;
import mut.MutationOperator;
import mut.SourceCodeMapping;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.util.CheckClassAdapter;

/**
 * Implementation of the ROR mutation operators using ASM lib
 * 
 * @author mike
 * 
 */
public class RORMutantCreator {

	public RORMutantCreator() {
	}

	public Set<Mutant> generateMutants(String classUnderTest,
			File originalClassFile) {

		Set<Mutant> result = new HashSet<Mutant>();

		for (int mutantNo = 0;; mutantNo++) {
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			ClassVisitor cc = new CheckClassAdapter(cw);
			MutationListener listener = new MutationListener();
			listener.mutantNo = mutantNo;

			// use this one to print out the class file info
			// ClassVisitor tv = new TraceClassVisitor(cc, new
			// PrintWriter(System.out));
			// ClassVisitor cv = new TransformingClassAdapter(tv, listener);
			ClassVisitor cv = new TransformingClassAdapter(cc, listener);

			ClassReader cr;
			try {
				cr = new ClassReader(new FileInputStream(originalClassFile));
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			cr.accept(cv, ClassReader.SKIP_DEBUG);
			byte[] newBytecode = cw.toByteArray();

			Mutant mutant = listener.mutant;
			mutant.setName(classUnderTest + "_ASM_ROR_" + listener.mutantNo);
			mutant.setByteCode(newBytecode);
			mutant.setClassName(classUnderTest);
			mutant.setMutationOperator(MutationOperator.ROR);

			// TODO implement source code ref
			SourceCodeMapping sourceMapping = new SourceCodeMapping();
			sourceMapping.setLineNo(-1);
			sourceMapping.setClassName(classUnderTest);
			sourceMapping.setSourceFile("not implemented");
			mutant.setSourceMapping(sourceMapping);

			result.add(mutant);

			if (listener.possibleMutations <= (mutantNo + 1)) {
				break;
			}
		}

		return result;
	}
}

class TransformingMethodAdapter extends MethodAdapter {
	MutationListener listener;

	@Override
	public void visitJumpInsn(int opcode, Label label) {

		final int IFEQ = 153;
		final int IFNE = 154;
		final int IF_ICMPEQ = 159;
		final int IF_ICMPNE = 160;

		switch (opcode) {
		case IFEQ:
			super.visitJumpInsn(listener.take("IFEQ -> IFNE") ? IFNE : opcode,
					label);
			break;
		case IFNE:
			super.visitJumpInsn(listener.take("IFNE -> IFEQ") ? IFEQ : opcode,
					label);

			break;
		case IF_ICMPEQ:
			super.visitJumpInsn(
					listener.take("IF_ICMPEQ -> IF_ICMPNE") ? IF_ICMPNE
							: opcode, label);
			break;
		case IF_ICMPNE:
			super.visitJumpInsn(
					listener.take("IF_ICMPNE -> IF_ICMPEQ") ? IF_ICMPEQ
							: opcode, label);
			break;

		default:
			super.visitJumpInsn(opcode, label);
			break;
		}

	}

	public TransformingMethodAdapter(MethodVisitor mv, MutationListener listener) {
		super(mv);
		this.listener = listener;
	}

}

class TransformingClassAdapter extends ClassAdapter {
	MutationListener listener;

	public TransformingClassAdapter(ClassVisitor cv, MutationListener listener) {
		super(cv);
		this.listener = listener;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		return new TransformingMethodAdapter(super.visitMethod(access, name,
				desc, signature, exceptions), listener);
	}

}

/**
 * creates mutant upon changes. the visitor goes through file, only the change
 * no == mutant no is applied
 */
class MutationListener {
	Mutant mutant = new Mutant();

	int possibleMutations;

	int mutantNo = 0;

	/**
	 * take the current mutation for this mutant?
	 * 
	 * @return
	 */
	public boolean take(String changeDescription) {
		boolean take;
		if (possibleMutations == mutantNo) {
			mutant.setChangeDescription(changeDescription);
			take = true;
		} else {
			take = false;
		}
		possibleMutations++;
		return take;
	}
}
