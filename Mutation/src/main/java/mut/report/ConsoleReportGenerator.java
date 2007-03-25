package mut.report;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import mut.IReportGenerator;
import mut.Mutant;
import mut.MutationTestSpec;

public class ConsoleReportGenerator implements IReportGenerator {
	private PrintStream out = System.out;

	public void generateReportOverall(OverallStatistics statistics) {
		out.println("---------------------------------------------------------");
		out.println("-------------- Overall REPORT ---------------------------");
		out.println("Number of overall mutants  : " + statistics.getNumberOfMutants());
		out.println("Number of overall survivors: " + statistics.getNumberOfSurvivors());
		out.println("-------------------------------------------------");

	}

	public void generateReport(MutationTestSpec mutationTest, List<Mutant> inputMutants, List<Mutant> survived,
			Map<String, Object> statistics) {
		out.println("----------------------------------------------------------");
		out.println("-------------- Detailed REPORT ---------------------------");
		out.println("Class under Test:   " + mutationTest.getClassUnderTest());
		out.println("Mutation Type       : " + mutationTest.getOperators());
		out.println("Test Set            : " + mutationTest.getTestSet());
		out.println("Number of mutants   : " + inputMutants.size());
		out.println("Number of survivors : " + survived.size());

		out.println("-------------------------------------------------");

		out.println("MUTANTS:");
		for (Mutant m : inputMutants) {
			out.println(m.getName() + ": OP=" + m.getMutationType() + " Change=" + m.getChangeDescription()
					+ " source=" + m.getSourceMapping());

		}

		out.println("-------------------------------------------------");
		out.println("SURVIVORS:");
		for (Mutant m : survived) {
			out.println(m.getName() + ": OP=" + m.getMutationType() + " Change=" + m.getChangeDescription()
					+ " source=" + m.getSourceMapping());

		}
		out.println("-------------------------------------------------");

	}

	public PrintStream getOut() {
		return out;
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}

}
