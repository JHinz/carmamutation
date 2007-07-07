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
