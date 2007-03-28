package com.mutation.events;

import java.util.Date;

public class ConsoleEventListener  implements IEventListener {

	public void notifyEvent(IEvent event) {
		System.out.println(new Date() +": "  +event);

	}

}
