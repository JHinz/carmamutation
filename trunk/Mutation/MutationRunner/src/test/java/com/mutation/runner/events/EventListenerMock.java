package com.mutation.runner.events;

import java.util.Vector;

import com.mutation.runner.events.IEvent;
import com.mutation.runner.events.IEventListener;

public class EventListenerMock implements IEventListener {

	private Vector<IEvent> events = new Vector<IEvent>();
	
	public void notifyEvent(IEvent event) {
		events.add(event);
	}

	public Vector<IEvent> getEvents() {
		return events;
	}

}
