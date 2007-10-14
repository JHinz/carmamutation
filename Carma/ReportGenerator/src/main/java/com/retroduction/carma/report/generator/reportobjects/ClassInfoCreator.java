package com.retroduction.carma.report.generator.reportobjects;

import java.util.ArrayList;
import java.util.List;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;

public class ClassInfoCreator {
	private List<ClassUnderTest> classes;
	public ClassInfoCreator(List<ClassUnderTest> classes){
		this.classes = classes;
	}
	
	public List<ClassInfo> createClassInfos(){
		List<ClassInfo> infos = new ArrayList<ClassInfo>(this.classes.size());
		for(ClassUnderTest clazz : this.classes){
			String fqName = clazz.getPackageName() +"." +clazz.getClassName();
			ClassInfo i = new ClassInfo(fqName, (int) clazz.getMutationRatio().getMutationCount(), (int) clazz.getMutationRatio().getSurvivorCount(), clazz.getExecutedTests());
			infos.add(i);
		}
		return infos;
	}
}
