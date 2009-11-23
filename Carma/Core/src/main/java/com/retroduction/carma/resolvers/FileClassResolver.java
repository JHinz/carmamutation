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
