package com.retroduction.carma.config;


/**
 *  spirng factory bean which allows to configure project dependencies at runtime
 */
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;

public class LibrariesFactoryBean implements FactoryBean {
	private URL[] libraryUrls;
	
	
	public void setDependenciesAsCsvFileNames(String csvFiles) throws MalformedURLException{
		String[] fileNames = csvFiles.split(",");
		List<File> files = new ArrayList<File>( fileNames.length);
		
		for(String f : fileNames){
			files.add( new File(f) );
			
		}
		this.setDependenciesAsFiles( files );
		
	}
	

	
	public void setDependenciesAsFiles(List<File> classesLocPaths) throws MalformedURLException {
		URL[] urls = new URL[classesLocPaths.size()];
		for (int i = 0; i < classesLocPaths.size(); i++) {
			urls[i] = classesLocPaths.get(i).toURL();
		}

		this.libraryUrls = urls;
	}
	
	public Object getObject() throws Exception {
		return this.libraryUrls;
	}

	public Class getObjectType() {
		return URL[].class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void setLibraryUrls(URL[] libraryUrls) {
		this.libraryUrls = libraryUrls;
	}

}
