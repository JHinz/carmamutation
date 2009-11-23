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

package com.retroduction.carma.core;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.testrunners.om.Mutant;
import com.retroduction.carma.core.api.transitions.ITransition;
import com.retroduction.carma.core.api.transitions.ITransitionGroup;

public class MutantGeneratorTestCase extends TestCase {

	private class MockTransition implements ITransition {

		public List<Mutant> applyTransitions(byte[] byteCode) {
			List<Mutant> result = new ArrayList<Mutant>();
			Mutant mutant = new Mutant();
			result.add(mutant);
			return result;
		}

		public String getName() {
			return "MockTransition";
		}

	}

	private class MockTransitionGroup implements ITransitionGroup {

		public String getName() {
			return "MockTransitionGroup";
		}

		public ITransition[] getTransitions() {
			return new ITransition[] { new MockTransition() };
		}

		public void initWithDefaultTransitions() {
		}

		public void setTransitions(ITransition[] transitions) {
		}

	}

	public void test() {

		MutantGenerator generator = new MutantGenerator();

		String classUnderTest = "com.test.someClass";
		byte[] originalClassByteCode = new byte[] { 0, 1, 2, 3, 4, 5 };

		ITransitionGroup group = new MockTransitionGroup();
		group.setTransitions(new ITransition[] { new MockTransition() });

		List<Mutant> result = generator.generateMutants(classUnderTest, originalClassByteCode, group);

		assertEquals(1, result.size());

		assertEquals(classUnderTest, result.get(0).getClassName());

	}
}
