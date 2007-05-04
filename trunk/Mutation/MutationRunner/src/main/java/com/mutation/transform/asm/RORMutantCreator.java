package com.mutation.transform.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.CheckClassAdapter;

import com.mutation.runner.EMutationOperator;
import com.mutation.runner.Mutant;
import com.mutation.runner.SourceCodeMapping;
import com.mutation.runner.events.IEventListener;

/**
 * Implementation of the ROR mutation operators using ASM lib
 * 
 * @author mike
 * 
 */
public class RORMutantCreator {

	public RORMutantCreator() {
	}

	public List<Mutant> generateMutants(String classUnderTest, byte[] byteCode, IEventListener eventListener) {

		List<Mutant> result = new ArrayList<Mutant>();

		ClassReader cr = new ClassReader(byteCode);

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

			cr.accept(cv, 0);// ClassReader.SKIP_DEBUG);
			byte[] newBytecode = cw.toByteArray();

			Mutant mutant = listener.mutant;
			mutant.setName(classUnderTest + "_ASM_ROR_" + listener.mutantNo);
			mutant.setByteCode(newBytecode);
			mutant.setClassName(classUnderTest);
			mutant.setMutationType(EMutationOperator.ROR);

			mutant.getSourceMapping().setClassName(classUnderTest);

			result.add(mutant);

			if (listener.possibleMutations <= (mutantNo + 1)) {
				break;
			}
		}

		return result;
	}
}

class TransformingMethodAdapter extends MethodAdapter {
	int currentLineNo = -2;

	@Override
	public void visitLineNumber(int no, Label start) {
		currentLineNo = no;
		super.visitLineNumber(no, start);
	}

	MutationListener listener;

	@Override
	public void visitJumpInsn(int opcode, Label label) {

		switch (opcode) {
		case Opcodes.IFEQ:
			super.visitJumpInsn(listener.take("IFEQ", "IFNE", currentLineNo) ? Opcodes.IFNE : opcode, label);
			break;
		case Opcodes.IFNE:
			super.visitJumpInsn(listener.take("IFNE", "IFEQ", currentLineNo) ? Opcodes.IFEQ : opcode, label);
			break;
		case Opcodes.IF_ICMPNE:
			super.visitJumpInsn(listener.take("IF_ICMPEQ", "IF_ICMPNE", currentLineNo) ? Opcodes.IF_ICMPEQ : opcode, label);
			break;
		case Opcodes.IF_ICMPEQ:
			super.visitJumpInsn(listener.take("IF_ICMPNE", "IF_ICMPEQ", currentLineNo) ? Opcodes.IF_ICMPNE : opcode, label);
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

	@Override
	public void visitSource(String source, String debug) {
		listener.mutant.getSourceMapping().setSourceFile(source);
		super.visitSource(source, debug);
	}

	public TransformingClassAdapter(ClassVisitor cv, MutationListener listener) {
		super(cv);
		this.listener = listener;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		return new TransformingMethodAdapter(super.visitMethod(access, name, desc, signature, exceptions), listener);
	}

}

/**
 * creates mutant upon changes. the visitor goes through file, only the change
 * no == mutant no is applied
 */
class MutationListener {
	Mutant mutant;

	int possibleMutations;

	int mutantNo = 0;

	MutationListener() {
		mutant = new Mutant();
		mutant.setSourceMapping(new SourceCodeMapping());
	}

	/**
	 * take the current mutation for this mutant?
	 * 
	 * @return
	 */
	public boolean take(String sourceInstruction, String targetInstruction, int lineNo) {
		boolean take;
		if (possibleMutations == mutantNo) {
			mutant.setSourceInstruction(sourceInstruction);
			mutant.setTargetInstruction(targetInstruction);
			mutant.getSourceMapping().setLineNo(lineNo);

			take = true;
		} else {
			take = false;
		}
		possibleMutations++;
		return take;
	}
}
