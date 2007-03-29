package com.mutation.transform.util;

import java.util.Vector;

import com.mutation.events.IEvent;
import com.mutation.events.IEventListener;

public class EventListenerMock implements IEventListener {

	private Vector<IEvent> events = new Vector<IEvent>();
	
	public void notifyEvent(IEvent event) {
		events.add(event);
	}

	public Vector<IEvent> getEvents() {
		return events;
	}

}
