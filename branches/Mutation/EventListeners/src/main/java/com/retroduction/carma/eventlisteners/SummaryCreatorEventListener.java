package com.retroduction.carma.eventlisteners;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StopWatch;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.api.eventlisteners.IEventListener;
import com.retroduction.carma.core.api.eventlisteners.om.ClassesUnderTestResolved;
import com.retroduction.carma.core.api.eventlisteners.om.MutantsGenerated;
import com.retroduction.carma.core.api.eventlisteners.om.MutationProcessStarted;
import com.retroduction.carma.core.api.eventlisteners.om.TestsExecuted;
import com.retroduction.carma.core.api.testrunners.om.Mutant;

/**
 * this event listeners creates summary information
 * 
 * @author mike
 * 
 */
public class SummaryCreatorEventListener implements IEventListener {

	int numClassesUnderTest;

	Set<String> testNames = new HashSet<String>();

	Set<String> testsNotExecuted = new HashSet<String>();

	Set<Mutant> totalMutants = new HashSet<Mutant>();

	Set<Mutant> suvivors = new HashSet<Mutant>();

	private StopWatch watch = new StopWatch();

	/**
	 * this method should be called after tests have been finished
	 * 
	 * @return
	 */
	public Summary createSummary() {
		Summary summary = new Summary();
		this.watch.stop();
		double elapsed = this.watch.getLastTaskTimeMillis();
		Set<String> executedTests = new HashSet<String>(this.testNames);
		executedTests.removeAll(this.testsNotExecuted);
		double mutantsPerClass = (double) this.totalMutants.size() / (double) this.numClassesUnderTest;
		double testsPerClass = (double) executedTests.size() / (double) this.numClassesUnderTest;
		double coverageRatio = 1.0 - ((double) this.suvivors.size() / (double) this.totalMutants.size());
		summary.mutantsPerClass = mutantsPerClass;
		summary.numClassesUnderTest = this.numClassesUnderTest;
		summary.numMutants = this.totalMutants.size();
		summary.numSurvivors = this.suvivors.size();
		summary.numTests = executedTests.size();
		summary.coverageRatioPercentage = coverageRatio * 100.0;
		summary.testsPerClass = testsPerClass;
		summary.timeSeconds = elapsed / 1000;
		return summary;
	}

	public void notifyEvent(IEvent event) {
		if (event instanceof MutationProcessStarted) {
			this.watch.start();
		} else if (event instanceof TestsExecuted) {
			TestsExecuted te = (TestsExecuted) event;
			if (!te.getMutant().isSurvived()) {
				this.suvivors.remove(te.getMutant());
			}
			TestsExecuted e = (TestsExecuted) event;
			this.testNames.addAll(e.getMutant().getExecutedTestsNames());
		} else if (event instanceof MutantsGenerated) {
			MutantsGenerated e = (MutantsGenerated) event;
			this.totalMutants.addAll(e.getGeneratedMutants());
			this.suvivors.addAll(e.getGeneratedMutants());
		} else if (event instanceof ClassesUnderTestResolved) {
			ClassesUnderTestResolved e = (ClassesUnderTestResolved) event;
			this.numClassesUnderTest += e.getClassUnderTestNames().size();
		}
	}

	public void destroy() {
	}

	public class Summary {
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
