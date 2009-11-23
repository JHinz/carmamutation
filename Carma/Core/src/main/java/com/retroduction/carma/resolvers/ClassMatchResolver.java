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

package com.retroduction.carma.resolvers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.retroduction.carma.core.api.resolvers.ITestClassResolver;
import com.retroduction.carma.utilities.ClassInfo;

public class ClassMatchResolver implements ITestClassResolver {

	private String testNameSuffix = "Test";
	private String testNamePrefix = "";

	public HashMap<String, Set<String>> resolve(Set<String> classNames) {

		HashMap<String, Set<String>> result = new HashMap<String, Set<String>>();

		for (String clazz : classNames) {

			ClassInfo info = new ClassInfo(clazz);
			String packagePrefix = info.getPackageName();
			if(0 != info.getPackageName().length()){
				packagePrefix += ".";
			}
			String testName = packagePrefix  +this.testNamePrefix +info.getClassName() + this.testNameSuffix;

			HashSet<String> tests = new HashSet<String>();

			tests.add(testName);

			result.put(clazz, tests);

		}

		return result;
	}

	public void setTestNameSuffix(String testNameSuffix) {
		this.testNameSuffix = testNameSuffix;
	}

	public void setTestNamePrefix(String testNamePrefix) {
		this.testNamePrefix = testNamePrefix;
	}

}
