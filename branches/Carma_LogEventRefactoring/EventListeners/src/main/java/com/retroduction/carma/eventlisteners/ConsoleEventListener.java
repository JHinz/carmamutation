package com.retroduction.carma.eventlisteners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.core.api.eventlisteners.IEvent;
import com.retroduction.carma.core.api.eventlisteners.IEventListener;

public class ConsoleEventListener implements IEventListener {

	private Log log = LogFactory.getLog(ConsoleEventListener.class);

	private boolean showSummaryOnly;

	public void notifyEvent(IEvent event) {
		if (!showSummaryOnly) {
			log.info(event);
		}
	}

	public void destroy() {
	}

	public void setShowSummaryOnly(boolean showSummaryOnly) {
		this.showSummaryOnly = showSummaryOnly;
	}

}
