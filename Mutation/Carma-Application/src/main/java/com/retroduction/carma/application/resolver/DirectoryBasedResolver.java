package com.retroduction.carma.application.resolver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.retroduction.carma.core.runner.ClassDescription;

/**
 * determines set of all classes within a directory of class files
 * 
 * @author mike
 * 
 */
public class DirectoryBasedResolver {

	/**
	 * base directory for classes
	 */
	private File[] classesBaseDir;

	public List<ClassDescription> determineClassNames() {
		List<ClassDescription> classDescriptions = new ArrayList<ClassDescription>();

		for (File classesDirectory : classesBaseDir)
			iterate(classesDirectory, "", "", classDescriptions);

		return classDescriptions;
	}

	public void setClassesBaseDir(File[] classesBaseDir) {
		this.classesBaseDir = classesBaseDir;
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
	 * @param classNames
	 *            set of class names to add classes found
	 */
	void iterate(File baseDir, String packagePrefix, String relPath, List<ClassDescription> classNames) {

		File[] files = baseDir.listFiles();
		if (files == null) {
			return;
		}

		for (File file : files) {
			if (file.isDirectory()) {
				String prefix = packagePrefix.equals("") ? file.getName() : packagePrefix + "." + file.getName();
				String relSubPath = relPath + "/" + file.getName();
				iterate(file, prefix, relSubPath, classNames);
			} else {
				String fileName = file.getName();

				if (fileName.endsWith(".class")) {
					String relClassName = fileName.substring(0, fileName.length() - ".class".length());

					ClassDescription desc = new ClassDescription();
					desc.setClassName(relClassName);
					desc.setPackageName(packagePrefix);
					desc.setClassFile(file.getPath());
					classNames.add(desc);
				}
			}
		}

	}
}
