package com.retroduction.carma.core.runner.events.listener;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StopWatch;

import com.retroduction.carma.core.runner.Mutant;
import com.retroduction.carma.core.runner.events.ClassesUnderTestResolved;
import com.retroduction.carma.core.runner.events.IEvent;
import com.retroduction.carma.core.runner.events.IEventListener;
import com.retroduction.carma.core.runner.events.MutantsGenerated;
import com.retroduction.carma.core.runner.events.MutationProcessFinished;
import com.retroduction.carma.core.runner.events.MutationProcessStarted;
import com.retroduction.carma.core.runner.events.TestSetDetermined;
import com.retroduction.carma.core.runner.events.TestsExecuted;

public class ConsoleEventListener implements IEventListener {
	int numSurvivors;

	int numKilled;

	int numClassesUnderTest;

	int numTests;

	private boolean showSummaryOnly;

	Set<Mutant> totalMutants = new HashSet<Mutant>();

	Set<Mutant> suvivors = new HashSet<Mutant>();

	private StopWatch watch = new StopWatch();

	public void notifyEvent(IEvent event) {
		if (!showSummaryOnly) {
			System.out.println(new Date() + ": " + event);
		}
		if (event instanceof MutationProcessStarted) {
			watch.start();
		} else if (event instanceof MutationProcessFinished) {
			watch.stop();
			double elapsed = watch.getLastTaskTimeMillis(); 
			double mutantsPerClass = (double) totalMutants.size() / (double) numClassesUnderTest;
			double testsPerClass = (double) numTests / (double) numClassesUnderTest;
			double survivorRatio = (double) suvivors.size() / (double) totalMutants.size() * 100;
			System.out.println("# --------------------------------------------------------------------------------");
			System.out.println("# TEST RESULTS SUMMARY ");
			System.out.println("#   Total time                : " + elapsed / 1000 + " sec.");
			System.out.println("#   Classes/Tests             : " + numClassesUnderTest +"/" +numTests);
			System.out.println("#   Tests Per Class           : " + testsPerClass);
			System.out.println("#   Mutants/Class             : " + mutantsPerClass);
			System.out.println("#   Mutants/Survivors         : "  + totalMutants.size() + "/" + suvivors.size());
			System.out.println("#   SurvivorRatio             : " +survivorRatio +" %");
			System.out.println("# --------------------------------------------------------------------------------");
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

	public void destroy() {
	}

	public void setShowSummaryOnly(boolean showSummaryOnly) {
		this.showSummaryOnly = showSummaryOnly;
	}

}
