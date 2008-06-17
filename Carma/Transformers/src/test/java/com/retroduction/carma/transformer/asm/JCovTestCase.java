package com.retroduction.carma.transformer.asm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.testrunners.om.SourceCodeMapping;
import com.retroduction.carma.utilities.ByteCodeFileReader;

public class JCovTestCase extends TestCase {

	public class MockASMTransformer extends AbstractASMTransition {

		@Override
		protected void checkNode(ClassNode classNode, MethodNode methodNode, List<Mutant> result,
				JCovInfo jcovInfo, AbstractInsnNode node) {

			if (node instanceof InsnNode) {

				SourceCodeMapping sourceMapping = new SourceCodeMapping();
				sourceMapping.setLineStart(jcovInfo.getStartLine());
				sourceMapping.setLineEnd(jcovInfo.getEndLine());
				sourceMapping.setColumnStart(jcovInfo.getStartColumn());
				sourceMapping.setColumnEnd(jcovInfo.getEndColumn());

				Mutant mutant = new Mutant();
				mutant.setSourceMapping(sourceMapping);

				result.add(mutant);
			}

		}

		public String getName() {
			return "MockASMTransition";
		}

	}

	private final static String SAMPLE_CLASS_FILENAME = "src/test/it/jcov/sources/Sample.class";

	public void testGetLineInfo() throws FileNotFoundException, IOException {

		byte[] byteCode = new ByteCodeFileReader().readByteCodeFromDisk(new File(SAMPLE_CLASS_FILENAME));

		MockASMTransformer asmTransformer = new MockASMTransformer();
		List<Mutant> mutants = asmTransformer.applyTransitions(byteCode);

		assertEquals(5, mutants.size());

		assertEquals(3, mutants.get(0).getSourceMapping().getLineStart());
		assertEquals(0, mutants.get(0).getSourceMapping().getColumnStart());
		assertEquals(3, mutants.get(0).getSourceMapping().getLineEnd());
		assertEquals(0, mutants.get(0).getSourceMapping().getColumnEnd());

		assertEquals(6, mutants.get(1).getSourceMapping().getLineStart());
		assertEquals(21, mutants.get(1).getSourceMapping().getColumnStart());
		assertEquals(7, mutants.get(1).getSourceMapping().getLineEnd());
		assertEquals(26, mutants.get(1).getSourceMapping().getColumnEnd());

		assertEquals(8, mutants.get(2).getSourceMapping().getLineStart());
		assertEquals(25, mutants.get(2).getSourceMapping().getColumnStart());
		assertEquals(9, mutants.get(2).getSourceMapping().getLineEnd());
		assertEquals(0, mutants.get(2).getSourceMapping().getColumnEnd());

		assertEquals(10, mutants.get(3).getSourceMapping().getLineStart());
		assertEquals(17, mutants.get(3).getSourceMapping().getColumnStart());
		assertEquals(11, mutants.get(3).getSourceMapping().getLineEnd());
		assertEquals(0, mutants.get(3).getSourceMapping().getColumnEnd());

		assertEquals(10, mutants.get(4).getSourceMapping().getLineStart());
		assertEquals(17, mutants.get(4).getSourceMapping().getColumnStart());
		assertEquals(11, mutants.get(4).getSourceMapping().getLineEnd());
		assertEquals(0, mutants.get(4).getSourceMapping().getColumnEnd());
	}

}
