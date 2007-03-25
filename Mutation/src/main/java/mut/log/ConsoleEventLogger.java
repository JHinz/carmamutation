package mut.log;

import java.util.Date;

public class ConsoleEventLogger implements IEventLogger {
	private String name;
	public ConsoleEventLogger(Object source){
		this.name = String.valueOf(source);
	}
	public ConsoleEventLogger(String name){
		this.name = name;
	}
	public void log(Event eventType, Object message) {
		String msg = "" +new Date() +" - " +name +" " +eventType +": " +message;
		System.out.println(msg);

	}

}
