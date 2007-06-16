package com.retroduction.carma.transformer.test.eventlisteners;

import java.util.Vector;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.api.eventlisteners.IEventListener;

public class EventListenerMock implements IEventListener {

	private Vector<IEvent> events = new Vector<IEvent>();
	
	public void notifyEvent(IEvent event) {
		events.add(event);
	}

	public Vector<IEvent> getEvents() {
		return events;
	}
	
	public void destroy() {
	}

}
