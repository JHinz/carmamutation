package com.retroduction.carma.core.runner.events.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;

import com.retroduction.carma.core.runner.events.IEvent;
import com.retroduction.carma.core.runner.events.IEventListener;

public class FileEventListener implements IEventListener {
	private PrintWriter writer;
	
	public FileEventListener(String fileName) throws FileNotFoundException{
		File file = new File(fileName);
		System.out.println(getClass().getSimpleName() +" writing to: " +file.getAbsolutePath());
		writer = new PrintWriter(file);
	}
	public void destroy(){
		writer.flush();
		writer.close();
		System.out.println(getClass().getSimpleName() + " finished.");
	}
	public void notifyEvent(IEvent event) {
		writer.println(new Date() +": " +event);
		writer.flush();
	}

}
