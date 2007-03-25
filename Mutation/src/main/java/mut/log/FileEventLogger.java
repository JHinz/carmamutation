package mut.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class FileEventLogger implements IEventLogger {
	private FileWriter fw;

	public FileEventLogger(String fileName) throws IOException {
		File file = new File(fileName);

		if (!file.exists()) {
			file.createNewFile();
		}

		fw = new FileWriter(file);
		System.out.println("# Writing logs to: " + file.getAbsolutePath());

	}

	public void log(Event eventType, Object message) {
		String msg = "" + new Date() + " - " + eventType + ": " + message
				+ "\n";
		try {

			fw.write(msg);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public void close() {
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
