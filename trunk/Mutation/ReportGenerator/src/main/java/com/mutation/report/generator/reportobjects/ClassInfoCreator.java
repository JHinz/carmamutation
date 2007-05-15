package com.mutation.report.generator.reportobjects;

import java.util.ArrayList;
import java.util.List;

import com.mutation.report.om.ClassUnderTest;

public class ClassInfoCreator {
	private List<ClassUnderTest> classes;
	public ClassInfoCreator(List<ClassUnderTest> classes){
		this.classes = classes;
	}
	
	public List<ClassInfo> createClassInfos(){
		List<ClassInfo> infos = new ArrayList<ClassInfo>(classes.size());
		for(ClassUnderTest clazz : classes){
			String fqName = clazz.getPackageName() +"." +clazz.getClassName();
			ClassInfo i = new ClassInfo(fqName, (int) clazz.getMutationRatio().getMutationCount(), (int) clazz.getMutationRatio().getSurvivorCount(), clazz.getExecutedTests());
			infos.add(i);
		}
		return infos;
	}
}
