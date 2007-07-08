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

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public int compareTo(PersistentClassInfo o) {
		return super.compareTo(o);
	}
}