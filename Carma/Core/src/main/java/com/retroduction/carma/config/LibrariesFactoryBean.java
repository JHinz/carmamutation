/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */

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

	public Class<URL[]> getObjectType() {
		return URL[].class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void setLibraryUrls(URL[] libraryUrls) {
		this.libraryUrls = libraryUrls;
	}

}
