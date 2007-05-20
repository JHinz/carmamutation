package com.mutation.runner.events.listener;

import java.util.HashSet;
import java.util.Set;

import com.mutation.runner.Mutant;
import com.mutation.runner.events.ClassesUnderTestResolved;
import com.mutation.runner.events.MutationProcessStarted;
import com.mutation.runner.events.IEvent;
import com.mutation.runner.events.IEventListener;
import com.mutation.runner.events.MutantsGenerated;
import com.mutation.runner.events.TestNotExecuted;
import com.mutation.runner.events.TestsExecuted;
import com.mutation.runner.utililties.StopWatch;

/**
 * this event listeners creates summary information
 * @author mike
 *
 */
public class SummaryCreatorEventListener implements IEventListener{

	int numClassesUnderTest;
	
	Set<String> testNames = new HashSet<String>();
	Set<String> testsNotExecuted = new HashSet<String>();

	Set<Mutant> totalMutants = new HashSet<Mutant>();

	Set<Mutant> suvivors = new HashSet<Mutant>();

	private StopWatch watch = new StopWatch();

	/**
	 * this method should be called after tests have been finished
	 * @return
	 */
	public Summary createSummary(){
		Summary summary = new Summary();
		double elapsed = watch.stop();
		Set<String> executedTests = new HashSet<String>(testNames);
		executedTests.removeAll(testsNotExecuted);
		double mutantsPerClass = (double) totalMutants.size() / (double) numClassesUnderTest;
		double testsPerClass = (double) executedTests.size() / (double) numClassesUnderTest;
		double coverageRatio = 1.0 - ((double) suvivors.size() / (double) totalMutants.size());
		summary.mutantsPerClass = mutantsPerClass;
		summary.numClassesUnderTest = numClassesUnderTest;
		summary.numMutants = totalMutants.size();
		summary.numSurvivors = suvivors.size();
		summary.numTests = executedTests.size();
		summary.coverageRatioPercentage = coverageRatio * 100.0;
		summary.testsPerClass = testsPerClass;
		summary.timeSeconds = (double)elapsed /1000;
		return summary;
	}
	public void notifyEvent(IEvent event) {
		if (event instanceof MutationProcessStarted) {
			watch.start();
		} else if (event instanceof TestsExecuted) {
			TestsExecuted te = (TestsExecuted) event;
			if (!te.isMutantSurvived()) {
				suvivors.remove(te.getMutant());
			} 
			TestsExecuted e = (TestsExecuted) event;
			testNames.addAll(e.getTestNames());
		} else if( event instanceof TestNotExecuted){
			TestNotExecuted e = (TestNotExecuted)event;
			testsNotExecuted.add(e.getTestCaseName());
		} 
		  else if (event instanceof MutantsGenerated) {
			MutantsGenerated e = (MutantsGenerated) event;
			totalMutants.addAll(e.getGeneratedMutants());
			suvivors.addAll(e.getGeneratedMutants());
		} else if (event instanceof ClassesUnderTestResolved) {
			ClassesUnderTestResolved e = (ClassesUnderTestResolved) event;
			numClassesUnderTest += e.getClassUnderTestNames().size();
		}
	}

	
	
	public class Summary{
		public double timeSeconds;
		public int numClassesUnderTest;
		public int numTests;
		public double testsPerClass;
		public double mutantsPerClass;
		public int numMutants;
		public int numSurvivors;
		public double coverageRatioPercentage;
	}
}