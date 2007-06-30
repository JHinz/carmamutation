package com.retroduction.carma.eventlisteners.util;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.Mutant;
import com.retroduction.carma.xmlreport.om.MutationRatio;
import com.retroduction.carma.xmlreport.om.MutationRun;

public class StatisticalReportAnalyzer {

	public void enhanceReport(MutationRun run) {

		if (run == null)
			return;

		long mutationCountForRun = 0;
		long survivorCountForRun = 0;

		for (ClassUnderTest clazz : run.getClassUnderTest()) {

			mutationCountForRun += clazz.getMutant().size();

			long survivorCountForClass = 0;
			long mutationCountForClass = clazz.getMutant().size();

			for (Mutant mutant : clazz.getMutant()) {

				if (mutant.isSurvived()) {
					survivorCountForRun++;
					survivorCountForClass++;
				}
			}

			MutationRatio clazzRatio = new MutationRatio();
			clazzRatio.setMutationCount(mutationCountForClass);
			clazzRatio.setSurvivorCount(survivorCountForClass);

			clazz.setMutationRatio(clazzRatio);
		}

		MutationRatio runRatio = new MutationRatio();
		runRatio.setMutationCount(mutationCountForRun);
		runRatio.setSurvivorCount(survivorCountForRun);

		run.setMutationRatio(runRatio);

	}
}
