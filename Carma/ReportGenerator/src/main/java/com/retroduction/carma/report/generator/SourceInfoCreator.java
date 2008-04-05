/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.retroduction.carma.report.om.SourceFile;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.Mutant;

public class SourceInfoCreator {
	private SourceFile sourceFile;
	private Map<Long, List<Mutant>> lineToMutants = new HashMap<Long, List<Mutant>>();
	public SourceInfoCreator(ClassUnderTest classUnderTest, SourceFile sourceFile){
		this.sourceFile = sourceFile;
		
		List<Mutant> mutants = classUnderTest.getMutant();
		for(Mutant m : mutants){
			Long line = m.getBaseSourceLine();
			if(!this.lineToMutants.containsKey(line)){
				this.lineToMutants.put(line, new ArrayList<Mutant>(1));
			}
			this.lineToMutants.get(line).add(m);
		}
	}
	
	public List<SourceLineInfo> createSourceInfo(){
		List<SourceLineInfo> list = new ArrayList<SourceLineInfo>();
		if(null == this.sourceFile.getSourceText()){
			return list;
		}
		long lineNo = 1;
		for(String text : this.sourceFile.getSourceText()){
			List<Mutant> mutants = this.lineToMutants.get(lineNo);
			SourceLineInfo info = new SourceLineInfo(lineNo++, text, mutants);
			list.add(info);
		}
		return list;
	}
}