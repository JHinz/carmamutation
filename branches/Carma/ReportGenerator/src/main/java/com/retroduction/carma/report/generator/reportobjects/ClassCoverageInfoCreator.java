package com.retroduction.carma.report.generator.reportobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.retroduction.carma.utilities.ClassNameUtil;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;

public class ClassCoverageInfoCreator {
	public CoverageInfo createCoverageInfo(ClassUnderTest cut) {
		String className = ClassNameUtil.getFqName(cut.getPackageName(), cut.getClassName());
		int numMutants = (int) cut.getMutationRatio().getMutationCount();
		int numSurvivors = (int) cut.getMutationRatio().getSurvivorCount();
		CoverageInfo info = new CoverageInfo(className, cut.getClassName(), 1, numMutants, numSurvivors);
		return info;
	}
	public List<CoverageInfo> createCoverageInfo(Collection<ClassUnderTest> cutList) {
		List<CoverageInfo> classes = new ArrayList<CoverageInfo>(cutList.size());
		for(ClassUnderTest cut : cutList){
			CoverageInfo info = this.createCoverageInfo(cut);
			classes.add(info);
		}
		Collections.sort(classes);
		return classes;
	}
}
