package com.retroduction.carma.core.api.eventlisteners;

public interface IEventListener {
	
	void notifyEvent(IEvent event);
	
	void destroy();

}
