package mut;

import java.util.Set;

public interface IReportGenerator {
	void generateReport(MutationTestSpec mutationTest, Set<Mutant> inputMutants, Set<Mutant> survived);
}
