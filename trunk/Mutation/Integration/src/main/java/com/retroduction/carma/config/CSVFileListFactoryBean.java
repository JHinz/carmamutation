package com.retroduction.carma.config;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
/**
 * helper class for reading a list of Files from a CSV string
 * @author mike
 *
 */
public class CSVFileListFactoryBean implements FactoryBean {
	private List<File> files;
	public Object getObject() throws Exception {
		return this.files;
	}

	public Class<List> getObjectType() {
		return List.class;
	}

	public boolean isSingleton() {
		return true;
	}

	
	public void setCsvNamesList(String csvFiles) throws MalformedURLException{
		String[] fileNames = csvFiles.split(",");
		List<File> filesList = new ArrayList<File>( fileNames.length);
		
		for(String f : fileNames){
			filesList.add( new File(f) );
			
		}
		this.files = filesList;
		
	}
}
