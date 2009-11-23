/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */

package org.retroduction.carma.reportgenerator.beanbuilder;

import java.util.List;

import junit.framework.TestCase;

import org.retroduction.carma.reportgenerator.beans.PackageDetailBean;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRatio;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * @author arau
 *
 */
public class PackageDetailBeanBuilderTestCase extends TestCase {

	public void test_WithSorting() {

		PackageListingBeanBuilder builder = new PackageListingBeanBuilder();

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
