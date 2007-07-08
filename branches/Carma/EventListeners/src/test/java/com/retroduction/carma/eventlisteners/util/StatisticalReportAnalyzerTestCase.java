package com.retroduction.carma.eventlisteners.util;

import java.util.Iterator;

import junit.framework.TestCase;

import com.retroduction.carma.annotations.TestClassToClassMapping;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.Mutant;
import com.retroduction.carma.xmlreport.om.MutationRun;

@TestClassToClassMapping(classNames = { "com.retroduction.carma.eventlisteners.util.StatisticalReportAnalyzer" })

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
