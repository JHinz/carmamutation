/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.resolvers;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.retroduction.carma.core.om.PersistentClassInfo;

/**
 * determines set of all classes within a directory of class files
 * 
 * @author mike
 * 
 */
public class FileClassResolver implements IClassResolver {

	/**
	 * base directory for classes
	 */
	private File[] classesPath;

	public void setClassesPath(File[] classesPath) {
		this.classesPath = classesPath;
	}

	public Set<PersistentClassInfo> determineClassNames() {

		HashSet<PersistentClassInfo> result = new HashSet<PersistentClassInfo>();

		for (File classesDirectory : this.classesPath) {
			this.iterate(classesDirectory, "", "", result);
		}

		return result;
	}

	public void setClassesBaseDir(File[] classesPath) {
		this.classesPath = classesPath;
	}

	/**
	 * iterate recursively over directory and add all class names of .class
	 * files as classes
	 * 
	 * @param baseDir
	 *            starting directory
	 * @param packagePrefix
	 *            prefix for that directory
	 * @param relPath
	 *            relative path from basedir
	 * @param classes
	 *            set of class names to add classes found
	 */
	void iterate(File baseDir, String packagePrefix, String relPath, Set<PersistentClassInfo> classes) {

		File[] files = baseDir.listFiles();
		if (files == null) {
			return;
		}

		for (File file : files) {

			if (file.isDirectory()) {
				String prefix = packagePrefix.equals("") ? file.getName() : packagePrefix + "." + file.getName();
				String relSubPath = relPath + "/" + file.getName();
				this.iterate(file, prefix, relSubPath, classes);
			} else {
				String fileName = file.getName();

				if (fileName.endsWith(".class")) {
					String relClassName = fileName.substring(0, fileName.length() - ".class".length());

					PersistentClassInfo classInfo = new PersistentClassInfo(relClassName, packagePrefix);
					classInfo.setClassFile(file.getPath());
					classes.add(classInfo);
				}
			}
		}

	}
}
