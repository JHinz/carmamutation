package com.retroduction.carma.eventlisteners;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StopWatch;

import com.retroduction.carma.core.api.transitions.events.ClassesUnderTestResolved;
import com.retroduction.carma.core.api.transitions.events.IEvent;
import com.retroduction.carma.core.api.transitions.events.IEventListener;
import com.retroduction.carma.core.api.transitions.events.MutantsGenerated;
import com.retroduction.carma.core.api.transitions.events.MutationProcessFinished;
import com.retroduction.carma.core.api.transitions.events.MutationProcessStarted;
import com.retroduction.carma.core.api.transitions.events.TestSetDetermined;
import com.retroduction.carma.core.api.transitions.events.TestsExecuted;
import com.retroduction.carma.core.runner.Mutant;

public class ConsoleEventListener implements IEventListener {

	private Log log = LogFactory.getLog(ConsoleEventListener.class);

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
			log.info(new Date() + ": " + event);
		}
		if (event instanceof MutationProcessStarted) {
			watch.start();
		} else if (event instanceof MutationProcessFinished) {
			watch.stop();
			double elapsed = watch.getLastTaskTimeMillis();
			double mutantsPerClass = (double) totalMutants.size() / (double) numClassesUnderTest;
			double testsPerClass = (double) numTests / (double) numClassesUnderTest;
			double survivorRatio = (double) suvivors.size() / (double) totalMutants.size() * 100;
			log.info("# --------------------------------------------------------------------------------");
			log.info("# TEST RESULTS SUMMARY ");
			log.info("#   Total time                : " + elapsed / 1000 + " sec.");
			log.info("#   Classes/Tests             : " + numClassesUnderTest + "/" + numTests);
			log.info("#   Tests Per Class           : " + testsPerClass);
			log.info("#   Mutants/Class             : " + mutantsPerClass);
			log.info("#   Mutants/Survivors         : " + totalMutants.size() + "/" + suvivors.size());
			log.info("#   SurvivorRatio             : " + survivorRatio + " %");
			log.info("# --------------------------------------------------------------------------------");
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
