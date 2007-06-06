package com.mutation.runner.events;

public interface IEventListener {
	
	void notifyEvent(IEvent event);
	
	void destroy();

}
