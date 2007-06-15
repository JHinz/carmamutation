package com.retroduction.carma.core.api.transitions.events;

public interface IEventListener {
	
	void notifyEvent(IEvent event);
	
	void destroy();

}
