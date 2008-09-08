/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.beanbuilder;

import java.util.ArrayList;
import java.util.List;

import org.retroduction.carma.reportgenerator.beans.ClassSummary;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;

/**
 * @author arau
 *
 */
public class ClassSummarizer {

	private List<ClassSummary> infos;

	public ClassSummarizer(List<ClassUnderTest> classes) {

		List<ClassSummary> infos = new ArrayList<ClassSummary>(classes.size());

		for (ClassUnderTest clazz : classes) {
			String fqName = clazz.getPackageName() + "." + clazz.getClassName();
			ClassSummary i = new ClassSummary(fqName, (int) clazz.getMutationRatio().getMutationCount(),
					(int) clazz.getMutationRatio().getSurvivorCount(), clazz.getExecutedTests());
			infos.add(i);
		}
	}

	public List<ClassSummary> getClassSumarries() {

		return infos;
	}
}
