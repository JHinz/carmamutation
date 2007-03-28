package mut;

import java.util.List;
import java.util.Map;

import com.mutation.Mutant;

public interface IReportGenerator {
	void generateReport(MutationTestSpec mutationTest, List<Mutant> inputMutants, List<Mutant> survived,
			Map<String, Object> statistics);

	// void generateReportOverall(OverallStatistics statistics);
}
