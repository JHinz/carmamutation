package com.mutation.runner.utililties;

import junit.framework.Test;

public class ClassLoaderInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		printLoader(Test.class);
		
	}
	
	public static void printLoader(Class clazz){
		System.out.println("Class: " +clazz);
		ClassLoader cl = clazz.getClassLoader();
		System.out.println("Loader: " +cl);
		StringBuffer s = new StringBuffer(" ");
		while((cl = cl.getParent()) != null){
			System.out.println(s +"" +cl);
			s.append("  ");
		}
	}
	
	
	
	

}
