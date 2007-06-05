package com.mutation.report.generator.reportobjects;

import java.util.Comparator;

import com.mutation.report.om.ClassUnderTest;

public class ClassUnderTestComparator implements Comparator<ClassUnderTest> {

	public int compare(ClassUnderTest o1, ClassUnderTest o2) {
		
		int packName = o1.getPackageName().compareTo(o2.getPackageName());
		if(0 != packName){
			return packName;
		}
		return o1.getClassName().compareTo(o2.getClassName());
	}
}
