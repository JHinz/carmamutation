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

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.Mutant;
import com.retroduction.carma.xmlreport.om.MutationRatio;
import com.retroduction.carma.xmlreport.om.MutationRun;

public class StatisticalReportAnalyzer {

	public void enhanceReport(MutationRun run) {

		if (run == null) {
			return;
		}

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
