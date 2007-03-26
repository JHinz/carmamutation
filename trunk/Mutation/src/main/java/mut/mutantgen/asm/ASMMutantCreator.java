package mut.mutantgen.asm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mut.EMutationType;
import mut.IMutantGenerator;
import mut.Mutant;

public class ASMMutantCreator implements IMutantGenerator {
	private File originalClassPath;

	public ASMMutantCreator(File originalClassPath) {
		this.originalClassPath = originalClassPath;
	}

	public List<Mutant> generateMutants(String classUnderTest,
			Set<EMutationType> operators) {

		String path = originalClassPath.getAbsolutePath() + "/"
				+ classUnderTest.replace('.', '/') + ".class";
		File originalClassFile = new File(path);

		List<Mutant> result = new ArrayList<Mutant>();
		if (operators.contains(EMutationType.ROR)) {
			RORMutantCreator creator = new RORMutantCreator();
			result.addAll(creator.generateMutants(classUnderTest,
					originalClassFile));
		}

		return result;
	}

}