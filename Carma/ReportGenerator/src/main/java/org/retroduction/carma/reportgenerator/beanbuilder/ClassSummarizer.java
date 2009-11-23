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
