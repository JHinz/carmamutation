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

package com.retroduction.carma.eventlisteners.util;

import java.util.Iterator;

import junit.framework.TestCase;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.Mutant;
import com.retroduction.carma.xmlreport.om.MutationRun;

public class StatisticalReportAnalyzerTestCase extends TestCase {

	public void test() {

		MutationRun run = new MutationRun();

		{
			ClassUnderTest clazz = new ClassUnderTest();
			clazz.setClassName("Class1");
			{
				Mutant mutant = new Mutant();
				mutant.setSurvived(false);
				clazz.getMutant().add(mutant);
			}
			{
				Mutant mutant = new Mutant();
				mutant.setSurvived(true);
				clazz.getMutant().add(mutant);
			}
			run.getClassUnderTest().add(clazz);
		}

		{
			ClassUnderTest clazz = new ClassUnderTest();
			clazz.setClassName("Class2");
			{
				Mutant mutant = new Mutant();
				mutant.setSurvived(false);
				clazz.getMutant().add(mutant);
			}
			{
				Mutant mutant = new Mutant();
				mutant.setSurvived(false);
				clazz.getMutant().add(mutant);
			}
			run.getClassUnderTest().add(clazz);
		}

		{
			ClassUnderTest clazz = new ClassUnderTest();
			clazz.setClassName("Class3");
			{
				Mutant mutant = new Mutant();
				mutant.setSurvived(true);
				clazz.getMutant().add(mutant);
			}
			{
				Mutant mutant = new Mutant();
				mutant.setSurvived(true);
				clazz.getMutant().add(mutant);
			}
			run.getClassUnderTest().add(clazz);
		}

		{
			ClassUnderTest clazz = new ClassUnderTest();
			clazz.setClassName("Class4");

			run.getClassUnderTest().add(clazz);
		}
		
		new StatisticalReportAnalyzer().enhanceReport(run);

		assertEquals(6, run.getMutationRatio().getMutationCount());
		assertEquals(3, run.getMutationRatio().getSurvivorCount());

		Iterator<ClassUnderTest> classes = run.getClassUnderTest().iterator();

		ClassUnderTest clazz;

		clazz = classes.next();

		assertEquals(2, clazz.getMutationRatio().getMutationCount());
		assertEquals(1, clazz.getMutationRatio().getSurvivorCount());

		clazz = classes.next();

		assertEquals(2, clazz.getMutationRatio().getMutationCount());
		assertEquals(0, clazz.getMutationRatio().getSurvivorCount());

		clazz = classes.next();

		assertEquals(2, clazz.getMutationRatio().getMutationCount());
		assertEquals(2, clazz.getMutationRatio().getSurvivorCount());

		clazz = classes.next();

		assertEquals(0, clazz.getMutationRatio().getMutationCount());
		assertEquals(0, clazz.getMutationRatio().getSurvivorCount());
	}
}
