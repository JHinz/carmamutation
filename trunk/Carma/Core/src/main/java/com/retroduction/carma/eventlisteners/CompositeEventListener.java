/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.eventlisteners;

import java.util.List;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.api.eventlisteners.IEventListener;

/**
 * delegates events to several event listeners
 * 
 * @author mike
 * 
 */
public class CompositeEventListener implements IEventListener {

	private List<IEventListener> listeners;

	public List<IEventListener> getListeners() {
		return this.listeners;
	}

	public void notifyEvent(IEvent event) {
		for (IEventListener l : this.listeners) {
			l.notifyEvent(event);
		}

	}

	public void setListeners(List<IEventListener> listeners) {
		this.listeners = listeners;
	}

	public void destroy() {

		for (IEventListener listener : this.getListeners()) {
			listener.destroy();
		}
	}

}
