package mut.report;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import mut.IReportGenerator;
import mut.Mutant;
import mut.MutationTestSpec;
import mut.SourceCodeMapping;

public class XMLReportGenerator implements IReportGenerator {
	private PrintStream out = System.out;
	private MutantSourceCreator sourceCreator;
	private boolean reportSurvivorsOnly = false;
	
	public void init(){
		System.out.println("PrintStream: " +getOut());
	}
	
	public void detroy(){
		getOut().close();
	}
	public void generateReport(MutationTestSpec mutationTest,
			List<Mutant> inputMutants, List<Mutant> survived, Map<String, Object> statistics) {

		String indent = "";
		
		out.println(indent +"<MutationTest>");
		indent += "  ";
		out.println(indent +"<ClassUnderTest>" +mutationTest.getClassUnderTest() +"</ClassUnderTest>");
		out.println(indent +"<MutationOperators>" +mutationTest.getOperators() +"</MutationOperators>");
		out.println(indent +"<TestSet>" +mutationTest.getTestSet() +"</TestSet>");
		out.println(indent +"<NumberOfMutants>" +inputMutants.size() +"</NumberOfMutants>");
		out.println(indent +"<NumberOfSurvivors>" +survived.size() +"</NumberOfSurvivors>");
		out.println(indent +"<Statistics>");
		indent += "  ";
			for(String name : statistics.keySet()){
				out.println(indent +"<Param name=\"" +name +"\">" +statistics.get(name) +"</Param>");
			}
		indent = indent.substring(2);
		out.println(indent +"</Statistics>");


		out.println(indent +"<Mutants>");
		indent += "  ";
		
		List<Mutant> mutants = isReportSurvivorsOnly() ? survived : inputMutants;
		
		for(Mutant m: mutants){
			out.println(indent +"<Mutant>");
			indent += "  ";
			out.println(indent +"<Name>" +m.getName() +"</Name>");
			if(survived.contains(m)){
				out.println(indent +"<Survived>true</Survived>");
			}else{
				out.println(indent +"<Survived>false</Survived>");
			}
			out.println(indent +"<MutationOperator>" +m.getMutationType() +"</MutationOperator>");
			out.println(indent +"<Change>" +m.getMutationType() +"</Change>");
			out.println(indent +"<SourceMapping>");
			indent = indent += "  ";
			SourceCodeMapping mapping = m.getSourceMapping();
			
			out.println(indent +"<ClassName>" +mapping.getClassName() +"</ClassName>");
			out.println(indent +"<SourceFile>" +mapping.getSourceFile() +"</SourceFile>");
			out.println(indent +"<LineNo>" +mapping.getLineNo() +"</LineNo>");
			indent = indent.substring(2);
			out.println(indent +"</SourceMapping>");
			if(null != sourceCreator){
				try {
					File sourceFile = sourceCreator.createMutantSourceCode(m);
					out.println(indent +"<MutantSourceFile>" +sourceFile.toURL() +"</MutantSourceFile>");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			indent = indent.substring(2);
			out.println(indent +"<Mutant>");
			
			
		}
		indent = indent.substring(2);
		out.println(indent +"</Mutants>");
		indent = indent.substring(2);

		out.println("</MutationTest>");

	}
	public PrintStream getOut() {
		return out;
	}
	public void setOut(PrintStream out) {
		this.out = out;
	}
	public boolean isReportSurvivorsOnly() {
		return reportSurvivorsOnly;
	}
	public void setReportSurvivorsOnly(boolean reportSurvivorsOnly) {
		this.reportSurvivorsOnly = reportSurvivorsOnly;
	}
	public void setSourceCreator(MutantSourceCreator sourceCreator) {
		this.sourceCreator = sourceCreator;
	}

}
