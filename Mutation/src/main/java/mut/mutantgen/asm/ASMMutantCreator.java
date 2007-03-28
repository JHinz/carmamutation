package mut.mutantgen.asm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mutation.EMutationOperator;
import com.mutation.IMutantGenerator;
import com.mutation.Mutant;
import com.mutation.events.IEventListener;


public class ASMMutantCreator implements IMutantGenerator {
	private File originalClassPath;

	public ASMMutantCreator(File originalClassPath) {
		this.originalClassPath = originalClassPath;
	}

	public List<Mutant> generateMutants(String classUnderTest,
			EMutationOperator operator, IEventListener listener) {

		String path = originalClassPath.getAbsolutePath() + "/"
				+ classUnderTest.replace('.', '/') + ".class";
		File originalClassFile = new File(path);

		List<Mutant> result = new ArrayList<Mutant>();
		if (operator.equals(EMutationOperator.ROR)) {
			RORMutantCreator creator = new RORMutantCreator();
			result.addAll(creator.generateMutants(classUnderTest,
					originalClassFile));
		}

		return result;
	}

}
