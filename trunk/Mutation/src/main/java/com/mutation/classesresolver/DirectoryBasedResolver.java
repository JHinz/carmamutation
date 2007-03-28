package com.mutation.classesresolver;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.mutation.IClassSetResolver;
import com.mutation.events.ClassesUnderTestResolved;
import com.mutation.events.IEventListener;

/**
 * determines set of all classes within a directory of class files
 * @author mike
 *
 */
public class DirectoryBasedResolver implements IClassSetResolver {
	/**
	 * base directory for classes
	 */
	private File classesBaseDir;

	public Set<String> determineClassNames(IEventListener eventListener) {
		Set<String> classNames = new HashSet<String>();
		iterate(this.classesBaseDir, "", "", classNames);
		
		eventListener.notifyEvent(new ClassesUnderTestResolved(classNames));
		return classNames;
	}

	public void setClassesBaseDir(File classesBaseDir) {
		this.classesBaseDir = classesBaseDir;
	}

	/**
	 * iterate recursively over directory and add all class names of .class files as classes
	 * @param baseDir starting directory
	 * @param packagePrefix prefix for that directory
	 * @param relPath relative path from basedir
	 * @param classNames set of class names to add classes found
	 */
	void iterate(File baseDir, String packagePrefix, String relPath,
			Set<String> classNames) {

		File[] files = baseDir.listFiles();
		if(files == null){
			return;
		}
		
		for (File file : files) {
			if (file.isDirectory()) {
				String prefix = packagePrefix.equals("") ? file.getName()
						: packagePrefix + "." + file.getName();
				String relSubPath = relPath + "/" + file.getName();
				iterate(file, prefix, relSubPath, classNames);
			} else {
				String fileName = file.getName();

				if (fileName.endsWith(".class")) {
					String relClassName = fileName.substring(0, fileName
							.length()
							- ".class".length());
					String className = packagePrefix + "." + relClassName;
					classNames.add(className);
				}
			}
		}

	}	
}
