/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.core.om;

import com.retroduction.carma.utilities.ClassInfo;
import com.retroduction.carma.utilities.ToStringUtils;

/**
 * 
 * Bean which describes a class file on the filesystem. Comparable on the fully
 * qualified class name property. Either the fully qualified class name or the
 * class name and package are constructed automatically. Properties related to
 * the fully qualified class name are immutable.
 * 
 * @author arau
 * 
 */
public class PersistentClassInfo extends ClassInfo {

	private String classFile;

	private String sourceFile;

	public PersistentClassInfo(String qualifiedClassName) {
		super(qualifiedClassName);
	}

	public PersistentClassInfo(String className, String packageName) {
		super(className, packageName);
	}

	public PersistentClassInfo(PersistentClassInfo classInfo) {
		super(classInfo.getFullyQualifiedClassName());
		this.setClassFile(classInfo.getClassFile());
		this.setSourceFile(classInfo.getSourceFile());
	}

	public String getClassFile() {
		return this.classFile;
	}

	public void setClassFile(String classFile) {
		this.classFile = classFile;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}

	public String getSourceFile() {
		return this.sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

}