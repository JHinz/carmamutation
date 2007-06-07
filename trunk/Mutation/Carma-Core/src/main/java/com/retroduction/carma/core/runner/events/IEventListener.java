package com.retroduction.carma.core.runner.events;

public interface IEventListener {
	
	void notifyEvent(IEvent event);
	
	void destroy();

}
