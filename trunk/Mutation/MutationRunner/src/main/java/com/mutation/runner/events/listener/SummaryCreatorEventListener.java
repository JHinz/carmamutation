package com.mutation.runner.events.listener;

import java.util.HashSet;
import java.util.Set;

import com.mutation.runner.Mutant;
import com.mutation.runner.events.ClassesUnderTestResolved;
import com.mutation.runner.events.DriverStarted;
import com.mutation.runner.events.IEvent;
import com.mutation.runner.events.IEventListener;
import com.mutation.runner.events.MutantsGenerated;
import com.mutation.runner.events.TestSetDetermined;
import com.mutation.runner.events.TestsExecuted;
import com.mutation.runner.utililties.StopWatch;

/**
 * this event listeners creates summary information
 * @author mike
 *
 */
public class SummaryCreatorEventListener implements IEventListener{
	int numSurvivors;

	int numKilled;

	int numClassesUnderTest;
	
	int numTests;

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
		double mutantsPerClass = (double) totalMutants.size() / (double) numClassesUnderTest;
		double testsPerClass = (double) numTests / (double) numClassesUnderTest;
		double survivorRatio = (double) suvivors.size() / (double) totalMutants.size() * 100;
		
		summary.mutantsPerClass = mutantsPerClass;
		summary.numClassesUnderTest = numClassesUnderTest;
		summary.numMutants = totalMutants.size();
		summary.numSurvivors = suvivors.size();
		summary.numTests = numTests;
		summary.survivorPercentage = survivorRatio;
		summary.testsPerClass = testsPerClass;
		summary.timeSeconds = (double)elapsed /1000;
		return summary;
	}
	public void notifyEvent(IEvent event) {
		if (event instanceof DriverStarted) {
			watch.start();
		} else if (event instanceof TestsExecuted) {
			TestsExecuted te = (TestsExecuted) event;
			if (te.isMutantSurvived()) {
				numSurvivors++;
			} else {
				suvivors.remove(te.getMutant());
				numKilled++;
			}
		} else if (event instanceof MutantsGenerated) {
			MutantsGenerated e = (MutantsGenerated) event;
			totalMutants.addAll(e.getGeneratedMutants());
			suvivors.addAll(e.getGeneratedMutants());
		} else if (event instanceof ClassesUnderTestResolved) {
			ClassesUnderTestResolved e = (ClassesUnderTestResolved) event;
			numClassesUnderTest += e.getClassUnderTestNames().size();
		} else if (event instanceof TestSetDetermined) {
			TestSetDetermined e = (TestSetDetermined) event;
			numTests += e.getDeterminedTests().size();
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
		public double survivorPercentage;
	}
}
