/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.utilities;

public class ClassNameUtil {
	public static String getFqName(String packageName, String className){
		if(null == packageName || packageName.length() == 0){
			return className;
		}
		return packageName +"." +className;
	}

	public static String getShortName(String fqName) {
		if(null == fqName || fqName.length() == 0){
			return fqName;
		}
		String[] elements = fqName.split("\\.");
		
		return elements[elements.length -1];
	}
}
