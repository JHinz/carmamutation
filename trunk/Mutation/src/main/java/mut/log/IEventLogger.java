package mut.log;

public interface IEventLogger {
	void log(Event eventType, Object message);
}
