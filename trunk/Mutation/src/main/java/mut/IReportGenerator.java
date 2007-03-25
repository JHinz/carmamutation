package mut;

import java.util.Map;
import java.util.Set;

public interface IReportGenerator {
	void generateReport(MutationTestSpec mutationTest, Set<Mutant> inputMutants, Set<Mutant> survived, Map<String, Object> statistics);
}
