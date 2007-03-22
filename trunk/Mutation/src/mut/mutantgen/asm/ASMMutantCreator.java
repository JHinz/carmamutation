package mut.mutantgen.asm;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import mut.IMutantGenerator;
import mut.Mutant;
import mut.MutationOperator;

public class ASMMutantCreator implements IMutantGenerator {
	private File originalClassPath;

	public ASMMutantCreator(File originalClassPath) {
		this.originalClassPath = originalClassPath;
	}

	public Set<Mutant> generateMutants(String classUnderTest,
			Set<MutationOperator> operators) {

		String path = originalClassPath.getAbsolutePath() + "/"
				+ classUnderTest.replace('.', '/') + ".class";
		File originalClassFile = new File(path);

		Set<Mutant> result = new HashSet<Mutant>();
		if (operators.contains(MutationOperator.ROR)) {
			RORMutantCreator creator = new RORMutantCreator();
			result.addAll(creator.generateMutants(classUnderTest,
					originalClassFile));
		}

		return result;
	}

}
