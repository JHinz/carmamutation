/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.beanbuilder;

import java.util.List;

import junit.framework.TestCase;

import org.retroduction.carma.reportgenerator.beans.PackageDetailBean;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRatio;
import com.retroduction.carma.xmlreport.om.MutationRun;

public class PackageDetailBeanBuilderTestCase extends TestCase {

	public void test_WithSorting() {

		PackageDetailBeanBuilder builder = new PackageDetailBeanBuilder();

		MutationRun report = new MutationRun();

		{
			ClassUnderTest clazz = new ClassUnderTest();
			clazz.setPackageName("package1");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(4);
			ratio.setSurvivorCount(1);

			clazz.setMutationRatio(ratio);

			report.getClassUnderTest().add(clazz);
		}

		{
			ClassUnderTest clazz = new ClassUnderTest();
			clazz.setPackageName("anotherPackage");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(4);
			ratio.setSurvivorCount(2);

			clazz.setMutationRatio(ratio);

			report.getClassUnderTest().add(clazz);
		}

		{
			ClassUnderTest clazz = new ClassUnderTest();
			clazz.setPackageName("package1");

			MutationRatio ratio = new MutationRatio();
			ratio.setMutationCount(4);
			ratio.setSurvivorCount(1);

			clazz.setMutationRatio(ratio);

			report.getClassUnderTest().add(clazz);
		}

		List<PackageDetailBean> result = builder.get(report);

		assertEquals(3, result.size());

		assertEquals("anotherPackage", result.get(1).getFqName());
		assertEquals(1, result.get(1).getNumberOfClasses());
		assertEquals(2, result.get(1).getNumberOfDefeatedMutations());
		assertEquals(2, result.get(1).getNumberOfSurvivedMutations());
		assertEquals(0.5, result.get(1).getCoverageLevel());

		assertEquals("package1", result.get(2).getFqName());
		assertEquals(2, result.get(2).getNumberOfClasses());
		assertEquals(6, result.get(2).getNumberOfDefeatedMutations());
		assertEquals(2, result.get(2).getNumberOfSurvivedMutations());
		assertEquals(0.75, result.get(2).getCoverageLevel());

		assertEquals("", result.get(0).getFqName());
		assertEquals(3, result.get(0).getNumberOfClasses());
		assertEquals(8, result.get(0).getNumberOfDefeatedMutations());
		assertEquals(4, result.get(0).getNumberOfSurvivedMutations());
		assertEquals(0.666, result.get(0).getCoverageLevel(), 0.01);

	}
}
