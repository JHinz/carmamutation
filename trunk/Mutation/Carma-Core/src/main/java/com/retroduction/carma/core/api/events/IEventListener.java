package com.retroduction.carma.core.api.events;

public interface IEventListener {
	
	void notifyEvent(IEvent event);
	
	void destroy();

}
