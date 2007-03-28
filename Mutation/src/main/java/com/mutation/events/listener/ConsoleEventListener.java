package com.mutation.events.listener;

import java.util.Date;

import com.mutation.events.DriverFinished;
import com.mutation.events.DriverStarted;
import com.mutation.events.IEvent;
import com.mutation.events.IEventListener;
import com.mutation.events.TestsExecuted;
import com.mutation.util.StopWatch;

public class ConsoleEventListener  implements IEventListener {
	int numSurvivers;
	int numKilled;
	private StopWatch watch = new StopWatch();
	public void notifyEvent(IEvent event) {
		System.out.println(new Date() +": "  +event);
		if(event instanceof DriverStarted){
			watch.start();
		}else if(event instanceof DriverFinished){
			double elapsed = watch.stop();
			System.out.println("# Finished. total time: " +elapsed/1000 +" sec.");
			System.out.println("# numSurvivers: " +numSurvivers +" numKilled: " +numKilled);
		}else if(event instanceof TestsExecuted){
			TestsExecuted te = (TestsExecuted) event;
			if(te.isMutantSurvived()){
				numSurvivers ++;
			}else{
				numKilled++;
			}
		}
	}

}
