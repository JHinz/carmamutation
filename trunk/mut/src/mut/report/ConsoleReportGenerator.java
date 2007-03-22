package mut.report;

import java.io.PrintStream;
import java.util.Set;

import mut.IReportGenerator;
import mut.Mutant;
import mut.MutationTestSpec;

public class ConsoleReportGenerator implements IReportGenerator {
	private PrintStream out = System.out;
	public void generateReport(MutationTestSpec mutationTest,
			Set<Mutant> inputMutants, Set<Mutant> survived) {
		out.println("-------------------------------------------------");
		out.println("-------------- REPORT ---------------------------");
		out.println("Class under Test:   " +mutationTest.getClassUnderTest());
		out.println("Mutation Operators  : " +mutationTest.getOperators());
		out.println("Test Set            : " +mutationTest.getTestSet());
		out.println("Number of mutants   : " +inputMutants.size());
		out.println("Number of survivors : " +survived.size());

		out.println("-------------------------------------------------");

		out.println("MUTANTS:");
		for(Mutant m: inputMutants){
			out.println(m.getName() +": OP=" +m.getMutationOperator() +" Change=" +m.getChangeDescription() +" source=" +m.getSourceMapping());
			
		}

		out.println("-------------------------------------------------");
		out.println("SURVIVORS:");
		for(Mutant m: survived){
			out.println(m.getName() +": OP=" +m.getMutationOperator() +" Change=" +m.getChangeDescription() +" source=" +m.getSourceMapping());
			
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
