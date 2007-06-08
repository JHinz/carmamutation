package com.retroduction.carma.core.runner.events.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.core.runner.events.IEvent;
import com.retroduction.carma.core.runner.events.IEventListener;

public class FileEventListener implements IEventListener {
	
	private Log log = LogFactory.getLog(FileEventListener.class);

	private PrintWriter writer;
	
	public FileEventListener(String fileName) throws FileNotFoundException{
		File file = new File(fileName);
		log.info(getClass().getSimpleName() +" writing to: " +file.getAbsolutePath());
		writer = new PrintWriter(file);
	}
	public void destroy(){
		writer.flush();
		writer.close();
		log.info(getClass().getSimpleName() + " finished.");
	}
	public void notifyEvent(IEvent event) {
		writer.println(new Date() +": " +event);
		writer.flush();
	}

}
