package com.retroduction.carma.report.generator.mock;

import java.util.Collection;
import java.util.List;

import com.retroduction.carma.report.generator.reportobjects.ClassCoverageInfoCreator;
import com.retroduction.carma.report.generator.reportobjects.CoverageInfo;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;

public class ClassCoverageInfoCreatorMock extends ClassCoverageInfoCreator {
	public List<CoverageInfo> result;
	public CoverageInfo  singleResult;
	public Collection<ClassUnderTest> cutList;
	public ClassUnderTest cut;
	@Override
	public List<CoverageInfo> createCoverageInfo(Collection<ClassUnderTest> cutList) {
		this.cutList = cutList;
		return result;
	}

	@Override
	public CoverageInfo createCoverageInfo(ClassUnderTest cut) {
		this.cut = cut;
		return singleResult;
	}
}
