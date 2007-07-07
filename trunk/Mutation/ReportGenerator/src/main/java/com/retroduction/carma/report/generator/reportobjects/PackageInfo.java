package com.retroduction.carma.report.generator.reportobjects;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
/**
 * provides package level information for a set of classes
 * @author mike
 *
 */
public class PackageInfo {
	private Map<String, Set<ClassUnderTest>> classesByPackage;

	public PackageInfo(Collection<ClassUnderTest> classes){
		classesByPackage = new HashMap<String, Set<ClassUnderTest>>();
		for(ClassUnderTest c : classes){
			String packageName = c.getPackageName();
			if(!classesByPackage.containsKey(packageName)){
				classesByPackage.put(packageName, new HashSet<ClassUnderTest>());
			}
			classesByPackage.get(packageName).add(c);
		}
	}
	// aggregate info on package level
	public Set<ClassUnderTest> getClassesInPackage(String packageName) {
		if (classesByPackage.containsKey(packageName)) {
			return classesByPackage.get(packageName);
		} else {
			return new HashSet<ClassUnderTest>();
		}
	}
	
	
	
	/**
	 * get all classes with given package prefix
	 * 
	 * @param packageName
	 * @return
	 */
	public Set<ClassUnderTest> getClassesByPackagePrefix(String packagePrefix) {
		Set<ClassUnderTest> result = new HashSet<ClassUnderTest>();
		if(null == packagePrefix){
			return result;
		}
		for(String pack : classesByPackage.keySet()){
			if(pack.startsWith(packagePrefix)){
				result.addAll( classesByPackage.get(pack) );
			}
		}
		return result;
	}
	
	public Set<String> getPackageNames(){
		return new TreeSet<String>(classesByPackage.keySet());
	}

	public Set<String> getSubPackages(String packagePrefix){
		if(null == packagePrefix || "".equals(packagePrefix)){
			return new TreeSet<String>(classesByPackage.keySet());
		}

		Set<String> packages = new TreeSet<String>();
		for(String name : classesByPackage.keySet()){
			if(name.startsWith(packagePrefix)){
				packages.add(name);
			}
		}
		return packages;
	}

}
