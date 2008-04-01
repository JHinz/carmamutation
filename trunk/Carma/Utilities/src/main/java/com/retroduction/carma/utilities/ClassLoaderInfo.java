/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.utilities;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassLoaderInfo {

	private static Log log = LogFactory.getLog(ClassLoaderInfo.class);

	public static void printLoader(Class<?> clazz) {
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
