package com.retroduction.carma.report.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mutation.report.om.ClassUnderTest;
import com.mutation.report.om.Mutant;
import com.mutation.report.source.om.SourceFile;

public class SourceInfoCreator {
	private SourceFile sourceFile;
	private Map<Long, List<Mutant>> lineToMutants = new HashMap<Long, List<Mutant>>();
	public SourceInfoCreator(ClassUnderTest classUnderTest, SourceFile sourceFile){
		this.sourceFile = sourceFile;
		
		List<Mutant> mutants = classUnderTest.getMutant();
		for(Mutant m : mutants){
			Long line = m.getBaseSourceLine();
			if(!lineToMutants.containsKey(line)){
				lineToMutants.put(line, new ArrayList<Mutant>(1));
			}
			lineToMutants.get(line).add(m);
		}
	}
	
	public List<SourceLineInfo> createSourceInfo(){
		List<SourceLineInfo> list = new ArrayList<SourceLineInfo>();
		if(null == sourceFile.getSourceText()){
			return list;
		}
		long lineNo = 1;
		for(String text : sourceFile.getSourceText()){
			List<Mutant> mutants = lineToMutants.get(lineNo);
			SourceLineInfo info = new SourceLineInfo(lineNo++, text, mutants);
			list.add(info);
		}
		return list;
	}
}
