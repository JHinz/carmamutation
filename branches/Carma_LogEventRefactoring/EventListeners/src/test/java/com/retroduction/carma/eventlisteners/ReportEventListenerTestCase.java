package com.retroduction.carma.eventlisteners;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import junit.framework.TestCase;

import com.retroduction.carma.core.api.eventlisteners.IEventListener;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingClassUnderTest;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingClassUnderTestFinished;
import com.retroduction.carma.core.api.eventlisteners.om.ProcessingMutant;
import com.retroduction.carma.core.api.eventlisteners.om.TestSetNotSane;
import com.retroduction.carma.core.api.eventlisteners.om.TestsExecuted;
import com.retroduction.carma.core.api.testrunners.om.ClassDescription;
import com.retroduction.carma.core.api.testrunners.om.SourceCodeMapping;
import com.retroduction.carma.core.api.transitions.ITransition;
import com.retroduction.carma.core.api.transitions.ITransitionGroup;
import com.retroduction.carma.xmlreport.om.ProcessingInfo;

public class ReportEventListenerTestCase extends TestCase {

	private class MockTransition implements ITransition {

		public List<com.retroduction.carma.core.api.testrunners.om.Mutant> applyTransitions(byte[] byteCode,
				IEventListener eventListener) {
			return null;
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

	public void test_GetDuration() throws JAXBException, DatatypeConfigurationException {

		ReportEventListener listener = new ReportEventListener(null);

		ProcessingInfo info = listener.createTimingInformation(42, 83);

		assertEquals(83 - 42, info.getDuration());

	}

	public void test_AllTestsSane_MutantSurvived_CheckTransferredInformation() throws JAXBException {

		ReportEventListener listener = new ReportEventListener(null);

		{
			ClassDescription clazz = new ClassDescription();

			clazz.setClassFile("classFile");
			clazz.setSourceFile("sourceFile");
			clazz.setClassName("className");
			clazz.setQualifiedClassName("qualifiedName");
			clazz.setPackageName("packageName");

			ProcessingClassUnderTest event = new ProcessingClassUnderTest(clazz);

			listener.notifyEvent(event);
		}

		com.retroduction.carma.core.api.testrunners.om.Mutant mutant = new com.retroduction.carma.core.api.testrunners.om.Mutant();

		{

			mutant.setClassName("targetClassName");
			mutant.setName("mutantName");
			mutant.setSurvived(true);
			mutant.setTransition(new MockTransition());
			mutant.setTransitionGroup(new MockTransitionGroup());

			SourceCodeMapping sourceCodeMapping = new SourceCodeMapping();
			sourceCodeMapping.setClassName("sourceCodeMappingClassName");
			sourceCodeMapping.setLineNo(42);
			sourceCodeMapping.setSourceFile("sourceFile");

			mutant.setSourceMapping(sourceCodeMapping);

			ProcessingMutant event = new ProcessingMutant(mutant);

			listener.notifyEvent(event);
		}

		{

			Set<String> executedTestNames = new HashSet<String>();
			executedTestNames.add("test1");
			executedTestNames.add("test2");

			Set<String> killerTestNames = new HashSet<String>();
			killerTestNames.add("test2");

			TestsExecuted event = new TestsExecuted(mutant, executedTestNames, true, killerTestNames);

			listener.notifyEvent(event);

		}

		{
			ProcessingClassUnderTestFinished event = new ProcessingClassUnderTestFinished();

			listener.notifyEvent(event);
		}

		assertEquals(1, listener.run.getClassUnderTest().size());
		assertEquals("classFile", listener.run.getClassUnderTest().get(0).getBaseClassFile());
		assertEquals("sourceFile", listener.run.getClassUnderTest().get(0).getBaseSourceFile());
		assertEquals("className", listener.run.getClassUnderTest().get(0).getClassName());
		assertEquals("packageName", listener.run.getClassUnderTest().get(0).getPackageName());

		HashSet<String> executedTestNames = new HashSet<String>();
		for (String testName : listener.run.getClassUnderTest().get(0).getExecutedTests())
			executedTestNames.add(testName);

		assertEquals(2, executedTestNames.size());
		assertTrue(executedTestNames.contains("test1"));
		assertTrue(executedTestNames.contains("test2"));

		assertEquals(0, listener.run.getClassUnderTest().get(0).getBrokenTests().size());

		assertEquals(1, listener.run.getClassUnderTest().get(0).getMutant().size());
		assertEquals(42, listener.run.getClassUnderTest().get(0).getMutant().get(0).getBaseSourceLine());
		assertEquals("mutantName", listener.run.getClassUnderTest().get(0).getMutant().get(0).getName());
		assertEquals("MockTransition", listener.run.getClassUnderTest().get(0).getMutant().get(0).getTransition());
		assertEquals("MockTransitionGroup", listener.run.getClassUnderTest().get(0).getMutant().get(0)
				.getTransitionGroup());
		assertEquals(1, listener.run.getClassUnderTest().get(0).getMutant().get(0).getKillerTests().size());
		assertEquals("test2", listener.run.getClassUnderTest().get(0).getMutant().get(0).getKillerTests().get(0));
	}

	public void test_TestSetNotSane() throws JAXBException {

		ReportEventListener listener = new ReportEventListener(null);

		{
			ClassDescription clazz = new ClassDescription();

			ProcessingClassUnderTest event = new ProcessingClassUnderTest(clazz);

			listener.notifyEvent(event);
		}

		{

			Set<String> testNames = new HashSet<String>();
			testNames.add("test1");
			testNames.add("test2");

			TestSetNotSane event = new TestSetNotSane(testNames);

			listener.notifyEvent(event);

		}

		{
			ProcessingClassUnderTestFinished event = new ProcessingClassUnderTestFinished();

			listener.notifyEvent(event);
		}

		assertEquals(1, listener.run.getClassUnderTest().size());

		assertEquals(2, listener.run.getClassUnderTest().get(0).getBrokenTests().size());

		HashSet<String> brokenTestNames = new HashSet<String>();
		for (String testName : listener.run.getClassUnderTest().get(0).getBrokenTests())
			brokenTestNames.add(testName);

		assertTrue(brokenTestNames.contains("test1"));
		assertTrue(brokenTestNames.contains("test2"));

		assertEquals(0, listener.run.getClassUnderTest().get(0).getExecutedTests().size());

		assertEquals(0, listener.run.getClassUnderTest().get(0).getMutant().size());
	}

}
