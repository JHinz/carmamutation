package com.retroduction.carma.core.runner.utililties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassLoaderInfo {

	private static Log log = LogFactory.getLog(ClassLoaderInfo.class);

	public static void printLoader(Class clazz) {
		log.info("Class: " + clazz);
		ClassLoader cl = clazz.getClassLoader();
		log.info("Loader: " + cl);
		StringBuffer s = new StringBuffer(" ");
		while ((cl = cl.getParent()) != null) {
			log.info(s + "" + cl);
			s.append("  ");
		}
	}

}
