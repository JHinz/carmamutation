package mut;

import java.util.List;
import java.util.Map;

import mut.report.OverallStatistics;

public interface IReportGenerator {
	void generateReport(MutationTestSpec mutationTest, List<Mutant> inputMutants, List<Mutant> survived,
			Map<String, Object> statistics);

	void generateReportOverall(OverallStatistics statistics);
}
