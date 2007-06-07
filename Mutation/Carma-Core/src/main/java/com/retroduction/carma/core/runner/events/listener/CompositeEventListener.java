package com.retroduction.carma.core.runner.events.listener;

import java.util.List;

import com.retroduction.carma.core.runner.events.IEvent;
import com.retroduction.carma.core.runner.events.IEventListener;

/**
 * delegates events to several event listeners
 * 
 * @author mike
 * 
 */
public class CompositeEventListener implements IEventListener {

	private List<IEventListener> listeners;

	public List<IEventListener> getListeners() {
		return listeners;
	}

	public void notifyEvent(IEvent event) {
		for (IEventListener l : listeners) {
			l.notifyEvent(event);
		}

	}

	public void setListeners(List<IEventListener> listeners) {
		this.listeners = listeners;
	}

	public void destroy() {

		for (IEventListener listener : getListeners()) {
			listener.destroy();
		}
	}

}
