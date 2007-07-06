package com.retroduction.carma.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.api.eventlisteners.IEventListener;
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

	private class MockEventListener implements IEventListener {

		public void destroy() {
		}

		public void notifyEvent(IEvent event) {
		}

	}

	public void test() {

		MutantGenerator generator = new MutantGenerator();

		String classUnderTest = "com.test.someClass";
		byte[] originalClassByteCode = new byte[] { 0, 1, 2, 3, 4, 5 };
		Set<ITransitionGroup> transitionGroups = new HashSet<ITransitionGroup>();

		ITransitionGroup group = new MockTransitionGroup();
		group.setTransitions(new ITransition[] { new MockTransition() });

		transitionGroups.add(group);
		List<Mutant> result = generator.generateMutants(classUnderTest, originalClassByteCode, transitionGroups);

		assertEquals(1, result.size());

		assertEquals(classUnderTest, result.get(0).getClassName());
		assertEquals(group.getName(), result.get(0).getTransitionGroup().getName());

	}
}
